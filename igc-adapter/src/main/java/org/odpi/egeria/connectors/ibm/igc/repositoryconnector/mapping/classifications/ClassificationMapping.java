/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestConstants;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSMetadataCollection;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCRepositoryHelper;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.InstanceMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.AttributeMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.EntityMapping;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.AttributeTypeDefCategory;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDefAttribute;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.ffdc.OMRSErrorCode;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.EntityNotKnownException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.TypeErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * The base class for all mappings between OMRS Classification TypeDefs and IGC assets.
 */
public abstract class ClassificationMapping extends InstanceMapping {

    private static final Logger log = LoggerFactory.getLogger(ClassificationMapping.class);

    private String igcAssetType;
    private List<String> igcRelationshipProperties;
    private String omrsEntityType;
    private String omrsClassificationType;
    private Set<String> excludeIgcAssetType;
    private List<InstanceStatus> omrsSupportedStatuses;
    private Set<String> mappedOmrsPropertyNames;

    private Map<String, EntityMapping.PropertyMapping> mappingByIgcProperty;
    private Map<String, EntityMapping.PropertyMapping> mappingByOmrsProperty;

    private List<ClassificationMapping> subtypes;

    public ClassificationMapping(String igcAssetType,
                                 String igcRelationshipProperty,
                                 String omrsEntityType,
                                 String omrsClassificationType) {
        this.igcAssetType = igcAssetType;
        this.igcRelationshipProperties = new ArrayList<>();
        this.igcRelationshipProperties.add(igcRelationshipProperty);
        this.omrsEntityType = omrsEntityType;
        this.omrsClassificationType = omrsClassificationType;
        this.excludeIgcAssetType = new HashSet<>();
        this.omrsSupportedStatuses = new ArrayList<>();
        this.mappedOmrsPropertyNames = new HashSet<>();
        this.mappingByIgcProperty = new HashMap<>();
        this.mappingByOmrsProperty = new HashMap<>();
        this.subtypes = new ArrayList<>();
        addSupportedStatus(InstanceStatus.ACTIVE);
        addSupportedStatus(InstanceStatus.DELETED);
    }

    /**
     * Add the provided status as one supported by this classification mapping.
     *
     * @param status a status that is supported by the mapping
     */
    public void addSupportedStatus(InstanceStatus status) { this.omrsSupportedStatuses.add(status); }

    /**
     * Retrieve the list of statuses that are supported by the classification mapping.
     *
     * @return {@code List<InstanceStatus>}
     */
    public List<InstanceStatus> getSupportedStatuses() { return this.omrsSupportedStatuses; }

    /**
     * Add the provided property name as one supported by this classification mapping.
     *
     * @param name the name of the OMRS property supported by the mapping
     */
    public void addMappedOmrsProperty(String name) { this.mappedOmrsPropertyNames.add(name); }

    /**
     * Retrieve the set of OMRS properties that are supported by the classification mapping.
     *
     * @return {@code Set<String>}
     */
    public Set<String> getMappedOmrsPropertyNames() {
        HashSet<String> omrsProperties = new HashSet<>(mappedOmrsPropertyNames);
        omrsProperties.addAll(getLiteralPropertyMappings());
        omrsProperties.addAll(mappingByOmrsProperty.keySet());
        return omrsProperties;
    }

    /**
     * Retrieve the IGC asset type to which this classification mapping applies.
     *
     * @return String
     */
    public String getIgcAssetType() { return this.igcAssetType; }

    /**
     * Retrieve the set of IGC properties used to apply this classification mapping.
     *
     * @return {@code Set<String>}
     */
    public Set<String> getMappedIgcPropertyNames() {
        HashSet<String> igcProperties = new HashSet<>(igcRelationshipProperties);
        igcProperties.addAll(mappingByIgcProperty.keySet());
        return igcProperties;
    }

    /**
     * Returns only the subset of mapped IGC properties that are simple one-to-one mappings to OMRS properties.
     *
     * @return {@code Set<String>}
     */
    public final Set<String> getSimpleMappedIgcProperties() {
        return mappingByIgcProperty.keySet();
    }

    /**
     * Retrieve the name of the OMRS ClassificationDef represented by this mapping.
     *
     * @return String
     */
    public String getOmrsClassificationType() { return this.omrsClassificationType; }

    /**
     * When the asset this applies to is a 'main_object', use this method to add any objects that should NOT be
     * included under that umbrella.
     *
     * @param igcAssetType the IGC asset type to exclude from 'main_object' consideration
     */
    public void addExcludedIgcAssetType(String igcAssetType) { this.excludeIgcAssetType.add(igcAssetType); }

    /**
     * When the asset this applies to is a 'main_object', this method details any objects that should NOT be
     * included under that umbrella.
     *
     * @return {@code Set<String>} - names of the IGC asset types that should not be included
     */
    public Set<String> getExcludedIgcAssetTypes() { return this.excludeIgcAssetType; }

    /**
     * Add the provided property as one of the IGC properties needed to setup this classification.
     *
     * @param property the IGC asset's property name
     */
    public void addIgcRelationshipProperty(String property) { this.igcRelationshipProperties.add(property); }

    /**
     * Add a simple one-to-one property mapping between an IGC property and an OMRS property.
     *
     * @param igcPropertyName the IGC property name to be mapped
     * @param omrsPropertyName the OMRS property name to be mapped
     */
    public final void addSimplePropertyMapping(String igcPropertyName, String omrsPropertyName) {
        EntityMapping.PropertyMapping pm = new EntityMapping.PropertyMapping(igcPropertyName, omrsPropertyName);
        mappingByOmrsProperty.put(omrsPropertyName, pm);
        mappingByIgcProperty.put(igcPropertyName, pm);
    }

    /**
     * Indicates whether this classification mapping has subtypes (true) or not (false).
     * Subtypes can be used where the same classification may represent classifications between a number of different
     * IGC objects and each needs to be distinguished to appropriately apply a mapping.
     *
     * @return boolean
     */
    public boolean hasSubTypes() { return !this.subtypes.isEmpty(); }

    /**
     * Adds a subtype to this classification mapping.
     * Subtypes can be used where the same classification may represent classifications between a number of different
     * IGC objects and each needs to be distinguished to appropriately apply a mapping.
     *
     * @param subClassificationMapping the classification mapping that defines a subtype of this mapping
     */
    public void addSubType(ClassificationMapping subClassificationMapping) { this.subtypes.add(subClassificationMapping); }

    /**
     * Retrieve the listing of sub-classification mappings of this mapping.
     * Subtypes can be used where the same classification may represent classifications between a number of different
     * IGC objects and each needs to be distinguished to appropriately apply a mapping.
     *
     * @return {@code List<ClassificationMapping>}
     */
    public List<ClassificationMapping> getSubTypes() { return this.subtypes; }

    /**
     * Implement this method to actually define the logic for the classification. (Since IGC has no actual concept
     * of classification, this is left as a method to-be-implemented depending on how the implementation desires the
     * classification to be represented within IGC.) By default, it will only apply simple and literal mappings that
     * have been defined for the classification.
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository via OMRS connector
     * @param classifications the list of classifications to which new classifications should be added
     * @param fromIgcObject the IGC object from which to determine the classifications
     * @param userId the user requesting the classifications (currently unused)
     */
    public void addMappedOMRSClassifications(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                             List<Classification> classifications,
                                             Reference fromIgcObject,
                                             String userId) {
        try {
            Classification classification = getMappedClassification(
                    igcomrsRepositoryConnector,
                    new InstanceProperties(),
                    fromIgcObject,
                    userId
            );
            classifications.add(classification);
        } catch (RepositoryErrorException e) {
            log.error("Unable to map classification.", e);
        }
    }

    /**
     * Implement this method to define how IGC assets can be searched based on this classification. (Since IGC has no
     * actual concept of classification, this is left as a method to-be-implemented depending on how the implementation
     * desires the classification to be represented within IGC.) By default, it will return an empty set of conditions
     * to find the classification.
     *
     * @param matchClassificationProperties the criteria to use when searching for the classification
     * @return IGCSearchConditionSet - the IGC search criteria to find entities based on this classification
     */
    public IGCSearchConditionSet getIGCSearchCriteria(InstanceProperties matchClassificationProperties) {
        return new IGCSearchConditionSet();
    }

    /**
     * Implement this method to define how to add an OMRS classification to an existing IGC asset. (Since IGC has no
     * actual concept of classification, this is left as a method to-be-implemented depending on how the implementation
     * desires the classification to be represented within IGC.)
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository via OMRS connector
     * @param igcAsset the IGC object to which to add the OMRS classification
     * @param entityGUID the GUID of the OMRS entity (ie. including any prefix)
     * @param initialProperties the set of classification-specific properties to add to the classification
     * @param userId the user requesting the classification to be added (currently unused)
     * @throws RepositoryErrorException on any mismatch between the requested classification and what IGC supports
     */
    public abstract void addClassificationToIGCAsset(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                     Reference igcAsset,
                                                     String entityGUID,
                                                     InstanceProperties initialProperties,
                                                     String userId)
            throws RepositoryErrorException;

    /**
     * Implement this method to define how to remove an OMRS classification from an existing IGC asset. (Since IGC has
     * no actual concept of classification, this is left as a method to-be-implemented depending on how the
     * implementation desires the classification to be represented within IGC.)
     *
     * @param igcomrsRepositoryConnector connectivity to the IGC repository via OMRS connector
     * @param igcAsset the IGC object from which to remove the OMRS classification
     * @param entityGUID the GUID of the OMRS entity (ie. including any prefix)
     * @param userId the user requesting the classification to be removed (currently unused)
     * @throws RepositoryErrorException on any mismatch between the requested classification and what IGC supports
     */
    public abstract void removeClassificationFromIGCAsset(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                          Reference igcAsset,
                                                          String entityGUID,
                                                          String userId)
            throws RepositoryErrorException;

    /**
     * Indicates whether this classification mapping matches the provided IGC asset type: that is, this mapping
     * can be used to translate to the provided IGC asset type.
     *
     * @param igcAssetType the IGC asset type to check the mapping against
     * @return boolean
     */
    public boolean matchesAssetType(String igcAssetType) {
        String simplifiedType = Reference.getAssetTypeForSearch(igcAssetType);
        if (log.isDebugEnabled()) { log.debug("checking for matching asset between {} and {}", this.igcAssetType, simplifiedType); }
        return (
                this.igcAssetType.equals(simplifiedType)
                        || (this.igcAssetType.equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE) && !this.excludeIgcAssetType.contains(simplifiedType))
        );
    }

    /**
     * Retrieve a Classification instance based on the provided information.
     *
     * @param igcomrsRepositoryConnector connector to the IGC repository
     * @param classificationProperties the properties to setup on the classification
     * @param fromIgcObject the IGC object against which the classification applies
     * @param userId the user through which the classification should be done
     * @return Classification
     * @throws RepositoryErrorException
     */
    protected Classification getMappedClassification(IGCOMRSRepositoryConnector igcomrsRepositoryConnector,
                                                     InstanceProperties classificationProperties,
                                                     Reference fromIgcObject,
                                                     String userId) throws RepositoryErrorException {

        final String methodName = "getMappedClassification";

        Classification classification = null;
        IGCRestClient igcRestClient = igcomrsRepositoryConnector.getIGCRestClient();
        IGCOMRSMetadataCollection igcomrsMetadataCollection = (IGCOMRSMetadataCollection) igcomrsRepositoryConnector.getMetadataCollection();
        OMRSRepositoryHelper omrsRepositoryHelper = igcomrsRepositoryConnector.getRepositoryHelper();
        String repositoryName = igcomrsRepositoryConnector.getRepositoryName();
        Map<String, TypeDefAttribute> omrsAttributeMap = igcomrsMetadataCollection.getTypeDefAttributesForType(omrsClassificationType);

        // Then we'll iterate through the provided mappings to set an OMRS instance property for each one
        for (String igcPropertyName : mappingByIgcProperty.keySet()) {
            String omrsAttribute = mappingByIgcProperty.get(igcPropertyName).getOmrsPropertyName();
            if (omrsAttributeMap.containsKey(omrsAttribute)) {
                TypeDefAttribute typeDefAttribute = omrsAttributeMap.get(omrsAttribute);
                classificationProperties = AttributeMapping.addPrimitivePropertyToInstance(
                        omrsRepositoryHelper,
                        repositoryName,
                        classificationProperties,
                        typeDefAttribute,
                        igcRestClient.getPropertyByName(fromIgcObject, igcPropertyName),
                        methodName
                );
            } else {
                if (log.isWarnEnabled()) { log.warn("No OMRS attribute {} defined for classification type {} -- skipping mapping.", omrsAttribute, omrsClassificationType); }
            }
        }

        // Set any fixed (literal) relationship property values
        for (String omrsPropertyName : getLiteralPropertyMappings()) {
            if (omrsAttributeMap.containsKey(omrsPropertyName)) {
                Object value = getOmrsPropertyLiteralValue(omrsPropertyName);
                if (value != null) {
                    TypeDefAttribute typeDefAttribute = omrsAttributeMap.get(omrsPropertyName);
                    AttributeTypeDefCategory attributeTypeDefCategory = typeDefAttribute.getAttributeType().getCategory();
                    if (attributeTypeDefCategory == AttributeTypeDefCategory.PRIMITIVE) {
                        classificationProperties = AttributeMapping.addPrimitivePropertyToInstance(
                                omrsRepositoryHelper,
                                repositoryName,
                                classificationProperties,
                                typeDefAttribute,
                                value,
                                methodName
                        );
                    } else {
                        classificationProperties.setProperty(omrsPropertyName, (InstancePropertyValue)value);
                    }
                }
            }
        }

        try {
            // Try to instantiate a new classification from the repository connector
            classification = igcomrsRepositoryConnector.getRepositoryHelper().getNewClassification(
                    igcomrsRepositoryConnector.getRepositoryName(),
                    userId,
                    omrsClassificationType,
                    omrsEntityType,
                    ClassificationOrigin.ASSIGNED,
                    null,
                    classificationProperties
            );
            // If modification details are available on the IGC object, add these to the classification,
            // including setting its version number based on the last update time
            if (igcRestClient.hasModificationDetails(fromIgcObject.getType())) {
                classification.setCreatedBy((String)igcRestClient.getPropertyByName(fromIgcObject, IGCRestConstants.MOD_CREATED_BY));
                classification.setCreateTime((Date)igcRestClient.getPropertyByName(fromIgcObject, IGCRestConstants.MOD_CREATED_ON));
                classification.setUpdateTime((Date)igcRestClient.getPropertyByName(fromIgcObject, IGCRestConstants.MOD_MODIFIED_ON));
                classification.setUpdatedBy((String)igcRestClient.getPropertyByName(fromIgcObject, IGCRestConstants.MOD_MODIFIED_BY));
                if (classification.getUpdateTime() != null) {
                    classification.setVersion(classification.getUpdateTime().getTime());
                }
            }
        } catch (TypeErrorException e) {
            log.error("Unable to create a new classification.", e);
            OMRSErrorCode errorCode = OMRSErrorCode.INVALID_CLASSIFICATION_FOR_ENTITY;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    omrsClassificationType,
                    omrsEntityType);
            throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                    ClassificationMapping.class.getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        return classification;

    }

}
