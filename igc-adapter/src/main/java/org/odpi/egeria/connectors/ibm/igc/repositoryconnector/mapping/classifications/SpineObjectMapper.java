/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ReferenceList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSErrorCode;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Classification;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.EntityDetail;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstancePropertyValue;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.EntityNotKnownException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Singleton defining the mapping to the OMRS "SpineObject" classification.
 */
public class SpineObjectMapper extends ClassificationMapping {

    private static final Logger log = LoggerFactory.getLogger(SpineObjectMapper.class);

    private static class Singleton {
        private static final SpineObjectMapper INSTANCE = new SpineObjectMapper();
    }
    public static SpineObjectMapper getInstance(IGCVersionEnum version) {
        return SpineObjectMapper.Singleton.INSTANCE;
    }

    private SpineObjectMapper() {
        super(
                "term",
                "category_path",
                "GlossaryTerm",
                "SpineObject"
        );
    }

    /**
     * Implements the SpineObject classification for IGC 'term' assets. Any term with a "Spine Objects" ancestor in
     * its category_path will be considered to be a Spine Object (and therefore be given a SpineObject classification).
     *
     * @param igcomrsRepositoryConnector
     * @param classifications
     * @param fromIgcObject
     * @param userId
     */
    @Override
    public void addMappedOMRSClassifications(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                             List<Classification> classifications,
                                             Reference fromIgcObject,
                                             String userId) {

        Identity termIdentity = fromIgcObject.getIdentity(igcomrsRepositoryConnector.getIGCRestClient());
        Identity catIdentity = termIdentity.getParentIdentity();

        if (catIdentity.toString().endsWith("Spine Objects")) {

            try {
                Classification classification = getMappedClassification(
                        igcomrsRepositoryConnector,
                        null,
                        fromIgcObject,
                        userId
                );
                classifications.add(classification);
            } catch (RepositoryErrorException e) {
                log.error("Unable to map SpineObject classification.", e);
            }

        }

    }

    /**
     * Search for SpineObject by looking at parent category of the term being under a "Spine Objects" category.
     * (There are no properties on the SpineObject classification, so no need to even check the provided
     * matchClassificationProperties.)
     *
     * @param matchClassificationProperties the criteria to use when searching for the classification
     * @return IGCSearchConditionSet - the IGC search criteria to find entities based on this classification
     */
    @Override
    public IGCSearchConditionSet getIGCSearchCriteria(InstanceProperties matchClassificationProperties) {

        IGCSearchCondition igcSearchCondition = new IGCSearchCondition(
                "parent_category.name",
                "=",
                "Spine Objects"
        );
        IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(igcSearchCondition);
        igcSearchConditionSet.setMatchAnyCondition(false);
        return igcSearchConditionSet;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addClassificationToIGCAsset(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                            Reference igcEntity,
                                            String entityGUID,
                                            InstanceProperties initialProperties,
                                            String userId) throws RepositoryErrorException {

        final String methodName = "addClassificationToIGCAsset";

        Map<String, InstancePropertyValue> classificationProperties = null;
        if (initialProperties != null) {
            classificationProperties = initialProperties.getInstanceProperties();
        }

        if (classificationProperties != null && !classificationProperties.isEmpty()) {

            if (log.isErrorEnabled()) { log.error("SpineObject classification has no properties, yet properties were included: {}", initialProperties); }
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CLASSIFICATION_EXCEEDS_REPOSITORY;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    getOmrsClassificationType(),
                    getIgcAssetType()
            );
            throw new RepositoryErrorException(
                    errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction()
            );

        } else {

            IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();

            IGCSearchCondition findCategory = new IGCSearchCondition(
                    "name",
                    "=",
                    "Spine Objects"
            );
            IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(findCategory);

            IGCSearch igcSearch = new IGCSearch("category", igcSearchConditionSet);
            ReferenceList results = igcRestClient.search(igcSearch);
            if (results == null || results.getPaging().getNumTotal() < 1) {
                log.error("No Spine Objects category found -- cannot continue.");
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CLASSIFICATION_NOT_FOUND;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        getOmrsClassificationType(),
                        entityGUID
                );
                throw new RepositoryErrorException(
                        errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction()
                );
            } else if (results.getPaging().getNumTotal() > 1) {
                log.warn("Found multiple Spine Objects categories, taking the first.");
            }
            String spineObjectCatRid = results.getItems().get(0).getId();
            IGCUpdate igcUpdate = new IGCUpdate(igcEntity.getId());
            igcUpdate.addExclusiveRelationship("parent_category", spineObjectCatRid);
            if (!igcRestClient.update(igcUpdate)) {
                if (log.isErrorEnabled()) { log.error("Unable to update entity {} to add classification {}.", entityGUID, getOmrsClassificationType()); }
            }

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeClassificationFromIGCAsset(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                 Reference igcAsset,
                                                 String entityGUID,
                                                 String userId)
            throws RepositoryErrorException {
        final String methodName = "removeClassificationFromIGCAsset";
        IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CLASSIFICATION_NOT_EDITABLE;
        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                getOmrsClassificationType(),
                entityGUID
        );
        throw new RepositoryErrorException(
                errorCode.getHTTPErrorCode(),
                this.getClass().getName(),
                methodName,
                errorMessage,
                errorCode.getSystemAction(),
                errorCode.getUserAction()
        );
    }

}
