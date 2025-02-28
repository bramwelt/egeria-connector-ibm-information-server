/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCRestClient;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Identity;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.ReferenceList;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearch;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchCondition;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchConditionSet;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.search.IGCSearchSorting;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCCreate;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.update.IGCUpdate;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.EntityMappingInstance;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.attributes.AttributeMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.entities.EntityMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.stores.*;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.relationships.RelationshipMapping;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.OMRSMetadataCollectionBase;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryHelper;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.repositoryconnector.OMRSRepositoryValidator;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Provides the OMRSMetadataCollection implementation for IBM InfoSphere Information Governance Catalog ("IGC").
 */
public class IGCOMRSMetadataCollection extends OMRSMetadataCollectionBase {

    private static final Logger log = LoggerFactory.getLogger(IGCOMRSMetadataCollection.class);

    private IGCRestClient igcRestClient;
    private IGCOMRSRepositoryConnector igcomrsRepositoryConnector;
    private IGCRepositoryHelper igcRepositoryHelper;

    private TypeDefStore typeDefStore;
    private AttributeMappingStore attributeMappingStore;

    /**
     * @param parentConnector      connector that this metadata collection supports.
     *                             The connector has the information to call the metadata repository.
     * @param repositoryName       name of this repository.
     * @param repositoryHelper     helper that provides methods to repository connectors and repository event mappers
     *                             to build valid type definitions (TypeDefs), entities and relationships.
     * @param repositoryValidator  validator class for checking open metadata repository objects and parameters
     * @param metadataCollectionId unique identifier for the repository
     */
    public IGCOMRSMetadataCollection(IGCOMRSRepositoryConnector parentConnector,
                                     String repositoryName,
                                     OMRSRepositoryHelper repositoryHelper,
                                     OMRSRepositoryValidator repositoryValidator,
                                     String metadataCollectionId) {
        super(parentConnector, repositoryName, repositoryHelper, repositoryValidator, metadataCollectionId);
        if (log.isDebugEnabled()) { log.debug("Constructing IGCOMRSMetadataCollection with name: {}", repositoryName); }
        parentConnector.setRepositoryName(repositoryName);
        this.igcRestClient = parentConnector.getIGCRestClient();
        this.igcomrsRepositoryConnector = parentConnector;
        this.igcRepositoryHelper = new IGCRepositoryHelper(igcomrsRepositoryConnector, repositoryHelper, igcRestClient);
        this.typeDefStore = new TypeDefStore();
        this.attributeMappingStore = new AttributeMappingStore(parentConnector);
    }

    /**
     * Retrieve the helper class for IGC-specific (deeper-dive) methods than what is provided by the
     * OMRSMetadataCollection interfaces.
     *
     * @return IGCRepositoryHelper
     */
    public IGCRepositoryHelper getIgcRepositoryHelper() { return this.igcRepositoryHelper; }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeDefGallery getAllTypes(String userId) throws
            RepositoryErrorException,
            InvalidParameterException {

        final String methodName = "getAllTypes";
        super.basicRequestValidation(userId, methodName);

        TypeDefGallery typeDefGallery = new TypeDefGallery();
        List<TypeDef> typeDefs = typeDefStore.getAllTypeDefs();
        if (log.isDebugEnabled()) { log.debug("Retrieved {} implemented TypeDefs for this repository.", typeDefs.size()); }
        typeDefGallery.setTypeDefs(typeDefs);

        List<AttributeTypeDef> attributeTypeDefs = attributeMappingStore.getAllAttributeTypeDefs();
        if (log.isDebugEnabled()) { log.debug("Retrieved {} implemented AttributeTypeDefs for this repository.", attributeTypeDefs.size()); }
        typeDefGallery.setAttributeTypeDefs(attributeTypeDefs);

        return typeDefGallery;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TypeDef> findTypeDefsByCategory(String userId, TypeDefCategory category) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "findTypeDefsByCategory";
        final String categoryParameterName = "category";
        super.typeDefCategoryParameterValidation(userId, category, categoryParameterName, methodName);

        List<TypeDef> typeDefs = new ArrayList<>();
        switch(category) {
            case ENTITY_DEF:
                typeDefs = igcRepositoryHelper.getMappedEntityTypes();
                break;
            case RELATIONSHIP_DEF:
                typeDefs = igcRepositoryHelper.getMappedRelationshipTypes();
                break;
            case CLASSIFICATION_DEF:
                typeDefs = igcRepositoryHelper.getMappedClassificationTypes();
                break;
        }
        return typeDefs;

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<AttributeTypeDef> findAttributeTypeDefsByCategory(String userId, AttributeTypeDefCategory category) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "findAttributeTypeDefsByCategory";
        final String categoryParameterName = "category";
        super.attributeTypeDefCategoryParameterValidation(userId, category, categoryParameterName, methodName);

        return attributeMappingStore.getAttributeTypeDefsByCategory(category);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TypeDef> findTypeDefsByProperty(String userId, TypeDefProperties matchCriteria) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "findTypeDefsByProperty";
        final String matchCriteriaParameterName = "matchCriteria";
        super.typeDefPropertyParameterValidation(userId, matchCriteria, matchCriteriaParameterName, methodName);

        List<TypeDef> typeDefs = typeDefStore.getAllTypeDefs();
        List<TypeDef> results = new ArrayList<>();
        if (matchCriteria != null) {
            Map<String, Object> properties = matchCriteria.getTypeDefProperties();
            for (TypeDef candidate : typeDefs) {
                List<TypeDefAttribute> candidateProperties = candidate.getPropertiesDefinition();
                for (TypeDefAttribute candidateAttribute : candidateProperties) {
                    String candidateName = candidateAttribute.getAttributeName();
                    if (properties.containsKey(candidateName)) {
                        results.add(candidate);
                    }
                }
            }
            results = typeDefs;
        }

        return results;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TypeDef> findTypesByExternalID(String userId,
                                               String standard,
                                               String organization,
                                               String identifier) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "findTypesByExternalID";
        super.typeDefExternalIDParameterValidation(userId, standard, organization, identifier, methodName);

        List<TypeDef> typeDefs = typeDefStore.getAllTypeDefs();
        List<TypeDef> results;
        if (standard == null && organization == null && identifier == null) {
            results = typeDefs;
        } else {
            results = new ArrayList<>();
            for (TypeDef typeDef : typeDefs) {
                List<ExternalStandardMapping> externalStandardMappings = typeDef.getExternalStandardMappings();
                for (ExternalStandardMapping externalStandardMapping : externalStandardMappings) {
                    String candidateStandard = externalStandardMapping.getStandardName();
                    String candidateOrg = externalStandardMapping.getStandardOrganization();
                    String candidateId = externalStandardMapping.getStandardTypeName();
                    if ( (standard == null || standard.equals(candidateStandard))
                            && (organization == null || organization.equals(candidateOrg))
                            && (identifier == null || identifier.equals(candidateId))) {
                        results.add(typeDef);
                    }
                }
            }
        }

        return results;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TypeDef> searchForTypeDefs(String userId, String searchCriteria) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "searchForTypeDefs";
        final String searchCriteriaParameterName = "searchCriteria";
        super.typeDefSearchParameterValidation(userId, searchCriteria, searchCriteriaParameterName, methodName);

        List<TypeDef> typeDefs = new ArrayList<>();
        for (TypeDef candidate : igcRepositoryHelper.getMappedEntityTypes()) {
            if (candidate.getName().matches(searchCriteria)) {
                typeDefs.add(candidate);
            }
        }
        for (TypeDef candidate : igcRepositoryHelper.getMappedRelationshipTypes()) {
            if (candidate.getName().matches(searchCriteria)) {
                typeDefs.add(candidate);
            }
        }
        for (TypeDef candidate : igcRepositoryHelper.getMappedClassificationTypes()) {
            if (candidate.getName().matches(searchCriteria)) {
                typeDefs.add(candidate);
            }
        }

        return typeDefs;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeDef getTypeDefByGUID(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotKnownException {

        final String methodName = "getTypeDefByGUID";
        final String guidParameterName = "guid";
        super.typeGUIDParameterValidation(userId, guid, guidParameterName, methodName);

        TypeDef found = typeDefStore.getTypeDefByGUID(guid);

        if (found == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    guid,
                    repositoryName);
            throw new TypeDefNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        return found;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttributeTypeDef getAttributeTypeDefByGUID(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotKnownException {

        final String methodName = "getAttributeTypeDefByGUID";
        final String guidParameterName = "guid";
        super.typeGUIDParameterValidation(userId, guid, guidParameterName, methodName);

        AttributeTypeDef found = attributeMappingStore.getAttributeTypeDefByGUID(guid);
        if (found == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ATTRIBUTE_TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    guid,
                    repositoryName);
            throw new TypeDefNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }
        return found;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeDef getTypeDefByName(String userId, String name) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotKnownException {

        final String methodName = "getTypeDefByName";
        final String nameParameterName = "name";
        super.typeNameParameterValidation(userId, name, nameParameterName, methodName);

        TypeDef found = typeDefStore.getTypeDefByName(name);

        if (found == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    name,
                    repositoryName);
            throw new TypeDefNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        return found;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AttributeTypeDef getAttributeTypeDefByName(String userId, String name) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotKnownException {

        final String methodName = "getAttributeTypeDefByName";
        final String nameParameterName = "name";
        super.typeNameParameterValidation(userId, name, nameParameterName, methodName);

        AttributeTypeDef found = attributeMappingStore.getAttributeTypeDefByName(name);
        if (found == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ATTRIBUTE_TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    name,
                    repositoryName);
            throw new TypeDefNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }
        return found;
    }

    /*
     * {@inheritDoc}
    @Override
    public void addTypeDefGallery(String userId, TypeDefGallery newTypes) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotSupportedException,
            TypeDefKnownException,
            TypeDefConflictException,
            InvalidTypeDefException,
            FunctionNotSupportedException,
            UserNotAuthorizedException {

        final String  methodName = "addTypeDefGallery";
        final String  galleryParameterName = "newTypes";

        this.validateRepositoryConnector(methodName);
        parentConnector.validateRepositoryIsActive(methodName);
        repositoryValidator.validateUserId(repositoryName, userId, methodName);
        repositoryValidator.validateTypeDefGallery(repositoryName, galleryParameterName, newTypes, methodName);

        List<AttributeTypeDef> attributeTypeDefs = newTypes.getAttributeTypeDefs();
        if (attributeTypeDefs != null) {
            for (AttributeTypeDef attributeTypeDef : attributeTypeDefs) {
                addAttributeTypeDef(userId, attributeTypeDef);
            }
        }

        List<TypeDef> typeDefs = newTypes.getTypeDefs();
        if (typeDefs != null) {
            for (TypeDef typeDef : typeDefs) {
                addTypeDef(userId, typeDef);
            }
        }

    } */

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTypeDef(String userId, TypeDef newTypeDef) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotSupportedException,
            TypeDefKnownException,
            TypeDefConflictException,
            InvalidTypeDefException {

        final String methodName = "addTypeDef";
        final String typeDefParameterName = "newTypeDef";
        super.newTypeDefParameterValidation(userId, newTypeDef, typeDefParameterName, methodName);

        TypeDefCategory typeDefCategory = newTypeDef.getCategory();
        String omrsTypeDefName = newTypeDef.getName();
        if (log.isDebugEnabled()) { log.debug("Looking for mapping for {} of type {}", omrsTypeDefName, typeDefCategory.getName()); }

        // See if we have a Mapper defined for the class -- if so, it's implemented
        StringBuilder sbMapperClassname = new StringBuilder();
        sbMapperClassname.append(IGCRepositoryHelper.MAPPING_PKG);
        switch(typeDefCategory) {
            case RELATIONSHIP_DEF:
                sbMapperClassname.append("relationships.");
                break;
            case CLASSIFICATION_DEF:
                sbMapperClassname.append("classifications.");
                break;
            case ENTITY_DEF:
                sbMapperClassname.append("entities.");
                break;
        }
        sbMapperClassname.append(omrsTypeDefName);
        sbMapperClassname.append("Mapper");

        try {

            Class mappingClass = Class.forName(sbMapperClassname.toString());
            if (log.isDebugEnabled()) { log.debug(" ... found mapping class: {}", mappingClass); }

            boolean success = false;

            switch(typeDefCategory) {
                case RELATIONSHIP_DEF:
                    success = igcRepositoryHelper.addRelationshipMapping(newTypeDef, mappingClass);
                    break;
                case CLASSIFICATION_DEF:
                    success = igcRepositoryHelper.addClassificationMapping(newTypeDef, mappingClass);
                    break;
                case ENTITY_DEF:
                    success = igcRepositoryHelper.addEntityMapping(newTypeDef, mappingClass);
                    break;
            }

            if (!success) {
                typeDefStore.addUnimplementedTypeDef(newTypeDef);
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        omrsTypeDefName,
                        repositoryName);
                throw new TypeDefNotSupportedException(errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            } else {
                typeDefStore.addTypeDef(newTypeDef);
            }

        } catch (ClassNotFoundException e) {
            typeDefStore.addUnimplementedTypeDef(newTypeDef);
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    omrsTypeDefName,
                    repositoryName);
            throw new TypeDefNotSupportedException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAttributeTypeDef(String userId, AttributeTypeDef newAttributeTypeDef) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotSupportedException,
            TypeDefKnownException,
            TypeDefConflictException,
            InvalidTypeDefException {

        final String methodName = "addAttributeTypeDef";
        final String typeDefParameterName = "newAttributeTypeDef";
        super.newAttributeTypeDefParameterValidation(userId, newAttributeTypeDef, typeDefParameterName, methodName);

        // Note this is only implemented for Enums, support for other types is indicated directly
        // in the verifyAttributeTypeDef method
        AttributeTypeDefCategory attributeTypeDefCategory = newAttributeTypeDef.getCategory();
        String omrsTypeDefName = newAttributeTypeDef.getName();
        if (log.isDebugEnabled()) { log.debug("Looking for mapping for {} of type {}", omrsTypeDefName, attributeTypeDefCategory.getName()); }

        // See if we have a Mapper defined for the class -- if so, it's implemented
        StringBuilder sbMapperClassname = new StringBuilder();
        sbMapperClassname.append(IGCRepositoryHelper.MAPPING_PKG);
        sbMapperClassname.append("attributes.");
        sbMapperClassname.append(omrsTypeDefName);
        sbMapperClassname.append("Mapper");

        try {
            Class mappingClass = Class.forName(sbMapperClassname.toString());
            if (log.isDebugEnabled()) { log.debug(" ... found mapping class: {}", mappingClass); }
            attributeMappingStore.addMapping(newAttributeTypeDef, mappingClass);
        } catch (ClassNotFoundException e) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ATTRIBUTE_TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    omrsTypeDefName,
                    repositoryName);
            throw new TypeDefNotSupportedException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean verifyTypeDef(String userId, TypeDef typeDef) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeDefNotSupportedException,
            InvalidTypeDefException {

        final String methodName = "verifyTypeDef";
        final String typeDefParameterName = "typeDef";
        super.typeDefParameterValidation(userId, typeDef, typeDefParameterName, methodName);

        String guid = typeDef.getGUID();

        // If we know the TypeDef is unimplemented, immediately throw an exception stating as much
        if (typeDefStore.getUnimplementedTypeDefByGUID(guid, false) != null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    typeDef.getName(),
                    repositoryName);
            throw new TypeDefNotSupportedException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else if (typeDefStore.getTypeDefByGUID(guid) != null) {

            boolean bVerified = true;
            List<String> issues = new ArrayList<>();

            Set<String> mappedProperties = new HashSet<>();
            switch (typeDef.getCategory()) {
                case ENTITY_DEF:
                    EntityMapping entityMapping = igcRepositoryHelper.getEntityMappingByGUID(guid);
                    if (entityMapping != null) {
                        mappedProperties = entityMapping.getAllMappedOmrsProperties();
                    }
                    break;
                case RELATIONSHIP_DEF:
                    RelationshipMapping relationshipMapping = igcRepositoryHelper.getRelationshipMappingByGUID(guid);
                    if (relationshipMapping != null) {
                        mappedProperties = relationshipMapping.getMappedOmrsPropertyNames();
                    }
                    break;
                case CLASSIFICATION_DEF:
                    ClassificationMapping classificationMapping = igcRepositoryHelper.getClassificationMappingByGUID(guid);
                    if (classificationMapping != null) {
                        mappedProperties = classificationMapping.getMappedOmrsPropertyNames();
                    }
                    break;
            }

            // Validate that we support all of the possible properties before deciding whether we
            // fully-support the TypeDef or not
            List<TypeDefAttribute> properties = typeDef.getPropertiesDefinition();
            if (properties != null) {
                for (TypeDefAttribute typeDefAttribute : properties) {
                    String omrsPropertyName = typeDefAttribute.getAttributeName();
                    if (!mappedProperties.contains(omrsPropertyName)) {
                        bVerified = false;
                        issues.add("property '" + omrsPropertyName + "' is not mapped");
                    }
                }
            }

            // If we were unable to verify everything, throw exception indicating it is not a supported TypeDef
            if (!bVerified) {
                if (log.isWarnEnabled()) { log.warn("Unable to verify type definition {}: {}", typeDef.getName(), String.join(", ", issues)); }
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        typeDef.getName(),
                        repositoryName);
                throw new TypeDefNotSupportedException(errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            } else {
                // Everything checked out, so return true
                return true;
            }

        } else {
            // It is completely unknown to us, so go ahead and try to addTypeDef
            return false;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean verifyAttributeTypeDef(String userId, AttributeTypeDef attributeTypeDef) throws
            InvalidParameterException,
            RepositoryErrorException,
            InvalidTypeDefException {

        final String methodName = "verifyAttributeTypeDef";
        final String typeDefParameterName = "attributeTypeDef";
        super.attributeTypeDefParameterValidation(userId, attributeTypeDef, typeDefParameterName, methodName);

        boolean bImplemented;
        switch (attributeTypeDef.getCategory()) {
            case PRIMITIVE:
            case COLLECTION:
                bImplemented = true;
                break;
            case ENUM_DEF:
                bImplemented = (attributeMappingStore.getAttributeTypeDefByGUID(attributeTypeDef.getGUID()) != null);
                break;
            default:
                bImplemented = false;
                break;
        }

        return bImplemented;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityDetail isEntityKnown(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "isEntityKnown";
        super.getInstanceParameterValidation(userId, guid, methodName);

        EntityDetail detail = null;
        try {
            detail = getEntityDetail(userId, guid);
        } catch (EntityNotKnownException e) {
            if (log.isInfoEnabled()) { log.info("Entity {} not known to the repository.", guid, e); }
        }
        return detail;

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public EntitySummary getEntitySummary(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException,
            EntityNotKnownException {

        final String methodName = "getEntitySummary";
        super.getInstanceParameterValidation(userId, guid, methodName);

        if (log.isDebugEnabled()) { log.debug("getEntitySummary with guid = {}", guid); }

        // Lookup the basic asset based on the RID (strip off prefix (indicating a generated type), if there)
        String possiblyPrefixedRid = igcRepositoryHelper.getRidFromGuid(guid);
        if (possiblyPrefixedRid == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    guid,
                    "null",
                    repositoryName);
            throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }
        String rid = IGCRepositoryHelper.getRidFromGeneratedId(possiblyPrefixedRid);
        Reference asset = this.igcRestClient.getAssetRefById(rid);

        EntitySummary summary;
        String prefix = IGCRepositoryHelper.getPrefixFromGeneratedId(possiblyPrefixedRid);

        // If we could not find any asset by the provided guid, throw an ENTITY_NOT_KNOWN exception
        if (asset == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    guid,
                    rid,
                    repositoryName);
            throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else if (asset.getType().equals(IGCRepositoryHelper.DEFAULT_IGC_TYPE)) {
            /* If the asset type returned has an IGC-listed type of 'main_object', it isn't one that the REST API
             * of IGC supports (eg. a data rule detail object, a column analysis master object, etc)...
             * Trying to further process it will result in failed REST API requests; so we should skip these objects */
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.UNSUPPORTED_OBJECT_TYPE;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    guid,
                    asset.getType(),
                    repositoryName);
            throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else {

            // Otherwise, retrieve the mapping dynamically based on the type of asset
            EntityMappingInstance entityMap = igcRepositoryHelper.getMappingInstanceForParameters(asset, prefix, userId);

            if (entityMap != null) {
                // 2. Apply the mapping to the object, and retrieve the resulting EntityDetail
                summary = EntityMapping.getEntitySummary(entityMap);
            } else {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        prefix + asset.getType(),
                        repositoryName);
                throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            }

        }

        return summary;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityDetail getEntityDetail(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException,
            EntityNotKnownException {

        final String methodName = "getEntityDetail";
        super.getInstanceParameterValidation(userId, guid, methodName);

        // Lookup the basic asset based on the RID (strip off prefix (indicating a generated type), if there)
        String possiblyPrefixedRid = igcRepositoryHelper.getRidFromGuid(guid);
        if (possiblyPrefixedRid == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    guid,
                    "null",
                    repositoryName);
            throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }
        String rid = IGCRepositoryHelper.getRidFromGeneratedId(possiblyPrefixedRid);
        Reference asset = this.igcRestClient.getAssetRefById(rid);

        return igcRepositoryHelper.getEntityDetail(userId, guid, asset);

    }

    /**
     * Return the relationships for a specific entity. Note that currently this will only work for relationships known
     * to (originated within) IGC, and that not all parameters are (yet) implemented.
     *
     * @param userId unique identifier for requesting user.
     * @param entityGUID String unique identifier for the entity.
     * @param relationshipTypeGUID String GUID of the the type of relationship required (null for all).
     * @param fromRelationshipElement the starting element number of the relationships to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param limitResultsByStatus Not implemented for IGC -- will only retrieve ACTIVE entities.
     * @param asOfTime Must be null (history not implemented for IGC).
     * @param sequencingProperty Must be null (there are no properties on IGC relationships).
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize -- the maximum number of result classifications that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @return Relationships list.  Null means no relationships associated with the entity.
     * @throws InvalidParameterException a parameter is invalid or null.
     * @throws TypeErrorException the type guid passed on the request is not known by the
     *                              metadata collection.
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                  the metadata collection is stored.
     * @throws EntityNotKnownException the requested entity instance is not known in the metadata collection.
     * @throws PropertyErrorException the sequencing property is not valid for the attached classifications.
     * @throws PagingErrorException the paging/sequencing parameters are set up incorrectly.
     * @throws FunctionNotSupportedException the repository does not support the asOfTime parameter.
     * @throws UserNotAuthorizedException the userId is not permitted to perform this operation.
     */
    @Override
    public List<Relationship> getRelationshipsForEntity(String userId,
                                                        String entityGUID,
                                                        String relationshipTypeGUID,
                                                        int fromRelationshipElement,
                                                        List<InstanceStatus> limitResultsByStatus,
                                                        Date asOfTime,
                                                        String sequencingProperty,
                                                        SequencingOrder sequencingOrder,
                                                        int pageSize) throws
            InvalidParameterException,
            TypeErrorException,
            RepositoryErrorException,
            EntityNotKnownException,
            PropertyErrorException,
            PagingErrorException,
            FunctionNotSupportedException,
            UserNotAuthorizedException {

        final String methodName = "getRelationshipsForEntity";
        super.getRelationshipsForEntityParameterValidation(
                userId,
                entityGUID,
                relationshipTypeGUID,
                fromRelationshipElement,
                limitResultsByStatus,
                asOfTime,
                sequencingProperty,
                sequencingOrder,
                pageSize
        );

        ArrayList<Relationship> alRelationships = new ArrayList<>();

        // Immediately throw unimplemented exception if trying to retrieve historical view or sequence by property
        if (asOfTime != null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.NO_HISTORY;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(repositoryName);
            throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else if (sequencingProperty != null
                || (sequencingOrder != null
                &&
                (sequencingOrder.equals(SequencingOrder.PROPERTY_ASCENDING)
                        || sequencingOrder.equals(SequencingOrder.PROPERTY_DESCENDING)))
        ) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.NO_RELATIONSHIP_PROPERTIES;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(repositoryName);
            throw new PropertyErrorException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else if (limitResultsByStatus == null
                || (limitResultsByStatus.size() == 1 && limitResultsByStatus.contains(InstanceStatus.ACTIVE))) {

            // Otherwise, only bother searching if we are after ACTIVE (or "all") entities -- non-ACTIVE means we
            // will just return an empty list

            // 0. see if the entityGUID has a prefix (indicating a generated type)
            String possiblyPrefixedRid = igcRepositoryHelper.getRidFromGuid(entityGUID);
            if (possiblyPrefixedRid == null) {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        entityGUID,
                        "null",
                        repositoryName);
                throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            }
            String rid = IGCRepositoryHelper.getRidFromGeneratedId(possiblyPrefixedRid);
            String prefix = IGCRepositoryHelper.getPrefixFromGeneratedId(possiblyPrefixedRid);

            // 1. retrieve entity from IGC by GUID (RID)
            Reference asset = this.igcRestClient.getAssetRefById(rid);

            // Ensure the entity actually exists (if not, throw error to that effect)
            if (asset == null) {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        entityGUID,
                        rid,
                        repositoryName);
                throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            } else {

                EntityMappingInstance entityMap = igcRepositoryHelper.getMappingInstanceForParameters(asset, prefix, userId);

                if (entityMap != null) {
                    // 2. Apply the mapping to the object, and retrieve the resulting relationships
                    alRelationships.addAll(
                            EntityMapping.getMappedRelationships(
                                    entityMap,
                                    relationshipTypeGUID,
                                    fromRelationshipElement,
                                    sequencingOrder,
                                    pageSize)
                    );
                } else {
                    IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
                    String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                            prefix + asset.getType(),
                            repositoryName);
                    throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                            this.getClass().getName(),
                            methodName,
                            errorMessage,
                            errorCode.getSystemAction(),
                            errorCode.getUserAction());
                }

            }

        }

        return alRelationships.isEmpty() ? null : alRelationships;

    }

    /**
     * Return a list of entities that match the supplied properties according to the match criteria.  The results
     * can be returned over many pages.
     *
     * @param userId unique identifier for requesting user.
     * @param entityTypeGUID String unique identifier for the entity type of interest (null means any entity type).
     * @param matchProperties Optional list of entity properties to match (where any String property's value should
     *                        be defined as a Java regular expression, even if it should be an exact match).
     * @param matchCriteria Enum defining how the properties should be matched to the entities in the repository.
     * @param fromEntityElement the starting element number of the entities to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param limitResultsByStatus Not implemented for IGC, only ACTIVE entities will be returned.
     * @param limitResultsByClassification List of classifications that must be present on all returned entities.
     * @param asOfTime Must be null (history not implemented for IGC).
     * @param sequencingProperty String name of the entity property that is to be used to sequence the results.
     *                           Null means do not sequence on a property name (see SequencingOrder).
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result entities that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @return a list of entities matching the supplied criteria where null means no matching entities in the metadata
     * collection.
     *
     * @throws InvalidParameterException a parameter is invalid or null.
     * @throws TypeErrorException the type guid passed on the request is not known by the
     *                              metadata collection.
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                    the metadata collection is stored.
     * @throws PropertyErrorException the properties specified are not valid for any of the requested types of
     *                                  entity.
     * @throws PagingErrorException the paging/sequencing parameters are set up incorrectly.
     * @throws FunctionNotSupportedException the repository does not support one of the provided parameters.
     * @throws UserNotAuthorizedException the userId is not permitted to perform this operation.
     * @see OMRSRepositoryHelper#getExactMatchRegex(String)
     */
    @Override
    public List<EntityDetail> findEntitiesByProperty(String userId,
                                                     String entityTypeGUID,
                                                     InstanceProperties matchProperties,
                                                     MatchCriteria matchCriteria,
                                                     int fromEntityElement,
                                                     List<InstanceStatus> limitResultsByStatus,
                                                     List<String> limitResultsByClassification,
                                                     Date asOfTime,
                                                     String sequencingProperty,
                                                     SequencingOrder sequencingOrder,
                                                     int pageSize) throws
            InvalidParameterException,
            TypeErrorException,
            RepositoryErrorException,
            PropertyErrorException,
            PagingErrorException,
            FunctionNotSupportedException,
            UserNotAuthorizedException {

        final String methodName = "findEntitiesByProperty";
        super.findEntitiesByPropertyParameterValidation(
                userId,
                entityTypeGUID,
                matchProperties,
                matchCriteria,
                fromEntityElement,
                limitResultsByStatus,
                limitResultsByClassification,
                asOfTime,
                sequencingProperty,
                sequencingOrder,
                pageSize
        );

        ArrayList<EntityDetail> entityDetails = new ArrayList<>();

        // Immediately throw unimplemented exception if trying to retrieve historical view
        if (asOfTime != null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.NO_HISTORY;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(repositoryName);
            throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else if (limitResultsByStatus == null
                || (limitResultsByStatus.size() == 1 && limitResultsByStatus.contains(InstanceStatus.ACTIVE))) {

            // Otherwise, only bother searching if we are after ACTIVE (or "all") entities -- non-ACTIVE means we
            // will just return an empty list

            // Short-circuit iterating through mappings if we are searching for something by qualifiedName,
            // in which case we should be able to infer the type we need to search based on the Identity implied
            // by the qualifiedName provided
            if (matchProperties != null
                    && matchProperties.getPropertyCount() == 1
                    && matchProperties.getPropertyNames().next().equals("qualifiedName")) {
                String qualifiedNameToFind = (String) ((PrimitivePropertyValue)matchProperties.getInstanceProperties().get("qualifiedName")).getPrimitiveValue();
                if (repositoryHelper.isExactMatchRegex(qualifiedNameToFind)) {
                    String unqualifiedName = repositoryHelper.getUnqualifiedLiteralString(qualifiedNameToFind);
                    String qualifiedName = unqualifiedName;
                    String prefix = null;
                    // We are overloading the use of the generated RIDs and prefixes here for qualified names as well...
                    if (IGCRepositoryHelper.isGeneratedRID(unqualifiedName)) {
                        prefix = IGCRepositoryHelper.getPrefixFromGeneratedId(unqualifiedName);
                        qualifiedName = IGCRepositoryHelper.getRidFromGeneratedId(unqualifiedName);
                    }
                    Identity identity = Identity.getFromString(qualifiedName, igcRestClient);
                    if (identity != null) {
                        String igcType = identity.getAssetType();
                        List<EntityMapping> mappers = igcRepositoryHelper.getMappers(igcType, userId);
                        for (EntityMapping mapper : mappers) {
                            String mapperPrefix = mapper.getIgcRidPrefix();
                            if ( (mapperPrefix == null && prefix == null)
                                    || (mapperPrefix != null && mapperPrefix.equals(prefix)) ) {

                                // validate mapped OMRS type against the provided entityTypeGUID (if non-null), and
                                // only proceed with the search if IGC identity is a (sub)type of the one requested
                                boolean runSearch = true;
                                if (entityTypeGUID != null) {
                                    String mappedOmrsTypeName = mapper.getOmrsTypeDefName();
                                    TypeDef entityTypeDef = repositoryHelper.getTypeDef(repositoryName,
                                            "entityTypeGUID",
                                            entityTypeGUID,
                                            methodName);
                                    runSearch = repositoryHelper.isTypeOf(metadataCollectionId, mappedOmrsTypeName, entityTypeDef.getName());
                                }
                                if (runSearch) {
                                    igcRepositoryHelper.processResultsForMapping(
                                            mapper,
                                            entityDetails,
                                            userId,
                                            matchProperties,
                                            matchCriteria,
                                            fromEntityElement,
                                            limitResultsByClassification,
                                            sequencingProperty,
                                            sequencingOrder,
                                            pageSize
                                    );
                                }
                            }
                        }
                    }
                }

            } else {

                // If we're searching for anything else, however, we need to iterate through all of the possible mappings
                // to ensure a full set of search results, so construct and run an appropriate search for each one
                List<EntityMapping> mappingsToSearch = getMappingsToSearch(entityTypeGUID, userId);

                for (EntityMapping mapping : mappingsToSearch) {

                    igcRepositoryHelper.processResultsForMapping(
                            mapping,
                            entityDetails,
                            userId,
                            matchProperties,
                            matchCriteria,
                            fromEntityElement,
                            limitResultsByClassification,
                            sequencingProperty,
                            sequencingOrder,
                            pageSize
                    );

                }

            }

        }

        return entityDetails.isEmpty() ? null : entityDetails;

    }

    /**
     * Return a list of entities that have the requested type of classification attached.
     *
     * @param userId unique identifier for requesting user.
     * @param entityTypeGUID unique identifier for the type of entity requested.  Null mans any type of entity.
     * @param classificationName name of the classification a null is not valid.
     * @param matchClassificationProperties list of classification properties used to narrow the search (where any String
     *                                      property's value should be defined as a Java regular expression, even if it
     *                                      should be an exact match).
     * @param matchCriteria Enum defining how the properties should be matched to the classifications in the repository.
     * @param fromEntityElement the starting element number of the entities to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param limitResultsByStatus Not implemented for IGC, only ACTIVE entities will be returned.
     * @param asOfTime Requests a historical query of the entity.  Null means return the present values. (not implemented
     *                 for IGC, must be null)
     * @param sequencingProperty String name of the entity property that is to be used to sequence the results.
     *                           Null means do not sequence on a property name (see SequencingOrder).
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result entities that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @return a list of entities matching the supplied criteria where null means no matching entities in the metadata
     * collection.
     * @throws InvalidParameterException a parameter is invalid or null.
     * @throws TypeErrorException the type guid passed on the request is not known by the
     *                              metadata collection.
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                    the metadata collection is stored.
     * @throws ClassificationErrorException the classification request is not known to the metadata collection.
     * @throws PropertyErrorException the properties specified are not valid for the requested type of
     *                                  classification.
     * @throws PagingErrorException the paging/sequencing parameters are set up incorrectly.
     * @throws FunctionNotSupportedException the repository does not support one of the provided parameters.
     * @throws UserNotAuthorizedException the userId is not permitted to perform this operation.
     * @see OMRSRepositoryHelper#getExactMatchRegex(String)
     */
    @Override
    public List<EntityDetail> findEntitiesByClassification(String userId,
                                                           String entityTypeGUID,
                                                           String classificationName,
                                                           InstanceProperties matchClassificationProperties,
                                                           MatchCriteria matchCriteria,
                                                           int fromEntityElement,
                                                           List<InstanceStatus> limitResultsByStatus,
                                                           Date asOfTime,
                                                           String sequencingProperty,
                                                           SequencingOrder sequencingOrder,
                                                           int pageSize) throws
            InvalidParameterException,
            TypeErrorException,
            RepositoryErrorException,
            ClassificationErrorException,
            PropertyErrorException,
            PagingErrorException,
            FunctionNotSupportedException,
            UserNotAuthorizedException {

        final String methodName = "findEntitiesByClassification";
        this.findEntitiesByClassificationParameterValidation(
                userId,
                entityTypeGUID,
                classificationName,
                matchClassificationProperties,
                matchCriteria,
                fromEntityElement,
                limitResultsByStatus,
                asOfTime,
                sequencingProperty,
                sequencingOrder,
                pageSize
        );

        ArrayList<EntityDetail> entityDetails = new ArrayList<>();

        // Immediately throw unimplemented exception if trying to retrieve historical view
        if (asOfTime != null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.NO_HISTORY;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(repositoryName);
            throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else if (limitResultsByStatus == null
                || (limitResultsByStatus.size() == 1 && limitResultsByStatus.contains(InstanceStatus.ACTIVE))) {

            // Otherwise, only bother searching if we are after ACTIVE (or "all") entities -- non-ACTIVE means we
            // will just return an empty list

            List<EntityMapping> mappingsToSearch = getMappingsToSearch(entityTypeGUID, userId);

            // Now iterate through all of the mappings we need to search, construct and run an appropriate search
            // for each one
            for (EntityMapping mapping : mappingsToSearch) {

                ClassificationMapping foundMapping = null;

                // Check which classifications (if any) are implemented for the entity mapping
                List<ClassificationMapping> classificationMappings = mapping.getClassificationMappers();
                for (ClassificationMapping classificationMapping : classificationMappings) {

                    // Check whether the implemented classification matches the one we're searching based on
                    String candidateName = classificationMapping.getOmrsClassificationType();
                    if (candidateName.equals(classificationName)) {
                        foundMapping = classificationMapping;
                        break;
                    }

                }

                // Only proceed if we have found a classification mapping for this entity that matches the search
                // criteria provided
                if (foundMapping != null) {

                    IGCSearch igcSearch = new IGCSearch();
                    igcSearch.addType(mapping.getIgcAssetType());
                    IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet();

                    // Retrieve the list of property names we need for the classification
                    Set<String> igcClassificationProperties = foundMapping.getMappedIgcPropertyNames();

                    // Compose the search criteria for the classification as a set of nested conditions, so that
                    // matchCriteria does not change the meaning of what we're searching
                    IGCSearchConditionSet baseCriteria = foundMapping.getIGCSearchCriteria(matchClassificationProperties);
                    igcSearchConditionSet.addNestedConditionSet(baseCriteria);

                    IGCSearchSorting igcSearchSorting = null;
                    if (sequencingProperty == null && sequencingOrder != null) {
                        igcSearchSorting = IGCRepositoryHelper.sortFromNonPropertySequencingOrder(sequencingOrder);
                    }

                    if (matchCriteria != null) {
                        switch (matchCriteria) {
                            case ALL:
                                igcSearchConditionSet.setMatchAnyCondition(false);
                                break;
                            case ANY:
                                igcSearchConditionSet.setMatchAnyCondition(true);
                                break;
                            case NONE:
                                igcSearchConditionSet.setMatchAnyCondition(false);
                                igcSearchConditionSet.setNegateAll(true);
                                break;
                        }
                    }

                    List<String> igcClassificationPropertiesList = new ArrayList<>(igcClassificationProperties);
                    igcSearch.addProperties(igcClassificationPropertiesList);
                    igcSearch.addConditions(igcSearchConditionSet);

                    igcRepositoryHelper.setPagingForSearch(igcSearch, fromEntityElement, pageSize);

                    if (igcSearchSorting != null) {
                        igcSearch.addSortingCriteria(igcSearchSorting);
                    }

                    igcRepositoryHelper.processResults(
                            mapping,
                            this.igcRestClient.search(igcSearch),
                            entityDetails,
                            pageSize,
                            userId
                    );

                } else {
                    if (log.isInfoEnabled()) { log.info("No classification mapping has been implemented for {} on entity {} -- skipping from search.", classificationName, mapping.getOmrsTypeDefName()); }
                }

            }

        }

        return entityDetails.isEmpty() ? null : entityDetails;

    }

    /**
     * Return a list of entities whose string based property values match the search criteria.  The
     * search criteria may include regex style wild cards.
     *
     * @param userId unique identifier for requesting user.
     * @param entityTypeGUID GUID of the type of entity to search for. Null means all types will
     *                       be searched (could be slow so not recommended).
     * @param searchCriteria String Java regular expression used to match against any of the String property values
     *                       within the entities of the supplied type, even if it should be an exact match.
     *                       (Retrieve all entities of the supplied type if this is either null or an empty string.)
     * @param fromEntityElement the starting element number of the entities to return.
     *                                This is used when retrieving elements
     *                                beyond the first page of results. Zero means start from the first element.
     * @param limitResultsByStatus Not implemented for IGC, only ACTIVE entities will be returned.
     * @param limitResultsByClassification List of classifications that must be present on all returned entities.
     * @param asOfTime Must be null (history not implemented for IGC).
     * @param sequencingProperty String name of the property that is to be used to sequence the results.
     *                           Null means do not sequence on a property name (see SequencingOrder).
     *                           (currently not implemented for IGC)
     * @param sequencingOrder Enum defining how the results should be ordered.
     * @param pageSize the maximum number of result entities that can be returned on this request.  Zero means
     *                 unrestricted return results size.
     * @return a list of entities matching the supplied criteria; null means no matching entities in the metadata
     * collection.
     * @throws InvalidParameterException a parameter is invalid or null.
     * @throws TypeErrorException the type guid passed on the request is not known by the
     *                              metadata collection.
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                    the metadata collection is stored.
     * @throws PropertyErrorException the sequencing property specified is not valid for any of the requested types of
     *                                  entity.
     * @throws PagingErrorException the paging/sequencing parameters are set up incorrectly.
     * @throws FunctionNotSupportedException the repository does not support one of the provided parameters.
     * @throws UserNotAuthorizedException the userId is not permitted to perform this operation.
     * @see OMRSRepositoryHelper#getExactMatchRegex(String)
     * @see OMRSRepositoryHelper#getContainsRegex(String)
     */
    @Override
    public List<EntityDetail> findEntitiesByPropertyValue(String userId,
                                                          String entityTypeGUID,
                                                          String searchCriteria,
                                                          int fromEntityElement,
                                                          List<InstanceStatus> limitResultsByStatus,
                                                          List<String> limitResultsByClassification,
                                                          Date asOfTime,
                                                          String sequencingProperty,
                                                          SequencingOrder sequencingOrder,
                                                          int pageSize) throws
            InvalidParameterException,
            TypeErrorException,
            RepositoryErrorException,
            PropertyErrorException,
            PagingErrorException,
            FunctionNotSupportedException,
            UserNotAuthorizedException {

        final String methodName = "findEntitiesByPropertyValue";
        super.findEntitiesByPropertyValueParameterValidation(
                userId,
                entityTypeGUID,
                searchCriteria,
                fromEntityElement,
                limitResultsByStatus,
                limitResultsByClassification,
                asOfTime,
                sequencingProperty,
                sequencingOrder,
                pageSize
        );

        ArrayList<EntityDetail> entityDetails = new ArrayList<>();

        // Immediately throw unimplemented exception if trying to retrieve historical view
        if (asOfTime != null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.NO_HISTORY;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(repositoryName);
            throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else if (limitResultsByStatus == null
                || (limitResultsByStatus.size() == 1 && limitResultsByStatus.contains(InstanceStatus.ACTIVE))) {

            // Otherwise, only bother searching if we are after ACTIVE (or "all") entities -- non-ACTIVE means we
            // will just return an empty list

            List<EntityMapping> mappingsToSearch = getMappingsToSearch(entityTypeGUID, userId);

            // Now iterate through all of the mappings we need to search, construct and run an appropriate search
            // for each one
            for (EntityMapping mapping : mappingsToSearch) {

                IGCSearch igcSearch = new IGCSearch();
                String igcAssetType = igcRepositoryHelper.addTypeToSearch(mapping, igcSearch);

                // Get POJO from the asset type, and use this to retrieve a listing of all string properties
                // for that asset type -- these are the list of properties we should use for the search
                Class pojo = igcRestClient.getPOJOForType(igcAssetType);

                if (pojo != null) {

                    IGCSearchConditionSet classificationLimiters = igcRepositoryHelper.getSearchCriteriaForClassifications(
                            igcAssetType,
                            limitResultsByClassification
                    );

                    if (limitResultsByClassification != null && !limitResultsByClassification.isEmpty() && classificationLimiters == null) {
                        if (log.isInfoEnabled()) { log.info("Classification limiters were specified, but none apply to the asset type {}, so excluding this asset type from search.", igcAssetType); }
                    } else {

                        IGCSearchConditionSet outerConditions = new IGCSearchConditionSet();

                        // If the searchCriteria is empty, retrieve all entities of the type (no conditions)
                        if (searchCriteria != null && !searchCriteria.equals("")) {

                            List<String> properties = igcRestClient.getStringPropertiesFromPOJO(igcAssetType);
                            // POST'd search to IGC doesn't work on v11.7.0.2 using long_description
                            // Using "searchText" requires using "searchProperties" (no "where" conditions) -- but does not
                            // work with 'main_object', must be used with a specific asset type
                            // Therefore for v11.7.0.2 we will simply drop long_description from the fields we search
                            if (igcRestClient.getIgcVersion().isEqualTo(IGCVersionEnum.V11702)) {
                                ArrayList<String> propertiesWithoutLongDescription = new ArrayList<>();
                                for (String property : properties) {
                                    if (!property.equals("long_description")) {
                                        propertiesWithoutLongDescription.add(property);
                                    }
                                }
                                properties = propertiesWithoutLongDescription;
                            }

                            IGCSearchConditionSet innerConditions = new IGCSearchConditionSet();
                            innerConditions.setMatchAnyCondition(true);
                            for (String property : properties) {
                                String unqualifiedValue = repositoryHelper.getUnqualifiedLiteralString(searchCriteria);
                                if (repositoryHelper.isContainsRegex(searchCriteria)) {
                                    innerConditions.addCondition(new IGCSearchCondition(
                                            property,
                                            "like %{0}%",
                                            unqualifiedValue
                                    ));
                                } else if (repositoryHelper.isStartsWithRegex(searchCriteria)) {
                                    innerConditions.addCondition(new IGCSearchCondition(
                                            property,
                                            "like {0}%",
                                            unqualifiedValue
                                    ));
                                } else if (repositoryHelper.isEndsWithRegex(searchCriteria)) {
                                    innerConditions.addCondition(new IGCSearchCondition(
                                            property,
                                            "like %{0}",
                                            unqualifiedValue
                                    ));
                                } else if (repositoryHelper.isExactMatchRegex(searchCriteria)) {
                                    innerConditions.addCondition(new IGCSearchCondition(
                                            property,
                                            "=",
                                            unqualifiedValue
                                    ));
                                } else {
                                    IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.REGEX_NOT_IMPLEMENTED;
                                    String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                                            repositoryName,
                                            searchCriteria);
                                    throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                                            this.getClass().getName(),
                                            methodName,
                                            errorMessage,
                                            errorCode.getSystemAction(),
                                            errorCode.getUserAction());
                                }
                            }
                            outerConditions.addNestedConditionSet(innerConditions);

                        }

                        if (classificationLimiters != null) {
                            outerConditions.addNestedConditionSet(classificationLimiters);
                            outerConditions.setMatchAnyCondition(false);
                        }

                        IGCSearchSorting igcSearchSorting = null;
                        if (sequencingProperty == null && sequencingOrder != null) {
                            igcSearchSorting = IGCRepositoryHelper.sortFromNonPropertySequencingOrder(sequencingOrder);
                        }

                        igcSearch.addConditions(outerConditions);

                        igcRepositoryHelper.setPagingForSearch(igcSearch, fromEntityElement, pageSize);

                        if (igcSearchSorting != null) {
                            igcSearch.addSortingCriteria(igcSearchSorting);
                        }

                        igcRepositoryHelper.processResults(
                                mapping,
                                this.igcRestClient.search(igcSearch),
                                entityDetails,
                                pageSize,
                                userId
                        );

                    }

                } else {
                    if (log.isWarnEnabled()) { log.warn("Unable to find POJO to handle IGC asset type '{}' -- skipping search against this asset type.", igcAssetType); }
                }

            }

        }

        return entityDetails.isEmpty() ? null : entityDetails;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Relationship isRelationshipKnown(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException {

        final String methodName = "isRelationshipKnown";
        super.getInstanceParameterValidation(userId, guid, methodName);

        Relationship relationship = null;
        try {
            relationship = getRelationship(userId, guid);
        } catch (RelationshipNotKnownException e) {
            if (log.isInfoEnabled()) { log.info("Could not find relationship {} in repository.", guid, e); }
        }
        return relationship;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Relationship getRelationship(String userId, String guid) throws
            InvalidParameterException,
            RepositoryErrorException,
            RelationshipNotKnownException {

        final String methodName = "getRelationship";
        super.getInstanceParameterValidation(userId, guid, methodName);

        if (log.isDebugEnabled()) { log.debug("Looking up relationship: {}", guid); }

        // Translate the key properties of the GUID into IGC-retrievables
        String relationshipRid = igcRepositoryHelper.getRidFromGuid(guid);
        if (relationshipRid == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.RELATIONSHIP_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    guid,
                    repositoryName);
            throw new RelationshipNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }
        String proxyOneRid = RelationshipMapping.getProxyOneRIDFromRelationshipRID(relationshipRid);
        String proxyTwoRid = RelationshipMapping.getProxyTwoRIDFromRelationshipRID(relationshipRid);
        String omrsRelationshipName = RelationshipMapping.getRelationshipTypeFromRelationshipRID(relationshipRid);

        String proxyOneIgcRid = proxyOneRid;
        String proxyTwoIgcRid = proxyTwoRid;

        if (IGCRepositoryHelper.isGeneratedRID(proxyOneRid)) {
            proxyOneIgcRid = IGCRepositoryHelper.getRidFromGeneratedId(proxyOneRid);
        }
        if (IGCRepositoryHelper.isGeneratedRID(proxyTwoRid)) {
            proxyTwoIgcRid = IGCRepositoryHelper.getRidFromGeneratedId(proxyTwoRid);
        }

        // Should not need to translate from proxyone / proxytwo to alternative assets, as the RIDs provided
        // in the relationship GUID should already be pointing to the correct assets
        String relationshipLevelRid = (proxyOneRid.equals(proxyTwoRid)) ? proxyOneRid : null;
        Reference proxyOne;
        Reference proxyTwo;
        RelationshipMapping relationshipMapping;
        if (relationshipLevelRid != null) {
            Reference relationshipAsset = igcRestClient.getAssetRefById(proxyOneIgcRid);
            String relationshipAssetType = relationshipAsset.getType();
            relationshipMapping = igcRepositoryHelper.getRelationshipMappingByTypes(
                    omrsRelationshipName,
                    relationshipAssetType,
                    relationshipAssetType
            );
            // Only need to translate if the RIDs are relationship-level RIDs
            proxyOne = relationshipMapping.getProxyOneAssetFromAsset(relationshipAsset, igcRestClient).get(0);
            proxyTwo = relationshipMapping.getProxyTwoAssetFromAsset(relationshipAsset, igcRestClient).get(0);
        } else {
            proxyOne = igcRestClient.getAssetRefById(proxyOneIgcRid);
            proxyTwo = igcRestClient.getAssetRefById(proxyTwoIgcRid);
            relationshipMapping = igcRepositoryHelper.getRelationshipMappingByTypes(
                    omrsRelationshipName,
                    proxyOne.getType(),
                    proxyTwo.getType()
            );
        }

        Relationship found;

        if (relationshipMapping != null) {
            try {

                RelationshipDef omrsRelationshipDef = (RelationshipDef) getTypeDefByName(userId, omrsRelationshipName);
                // Since the ordering should be set by the GUID we're lookup up anyway, we'll simply set the property
                // to one of the proxyOne properties
                String igcPropertyName = relationshipMapping.getProxyOneMapping().getIgcRelationshipProperties().get(0);
                if (log.isDebugEnabled()) { log.debug(" ... using property: {}", igcPropertyName); }
                found = RelationshipMapping.getMappedRelationship(
                        igcomrsRepositoryConnector,
                        relationshipMapping,
                        omrsRelationshipDef,
                        proxyOne,
                        proxyTwo,
                        igcPropertyName,
                        userId,
                        relationshipLevelRid
                );

            } catch (TypeDefNotKnownException e) {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        omrsRelationshipName,
                        repositoryName);
                throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            }
        } else {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    omrsRelationshipName,
                    repositoryName);
            throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        return found;

    }

    /**
     * {@inheritDoc}
     */
    public List<Relationship> findRelationshipsByProperty(String userId,
                                                          String relationshipTypeGUID,
                                                          InstanceProperties matchProperties,
                                                          MatchCriteria matchCriteria,
                                                          int fromRelationshipElement,
                                                          List<InstanceStatus> limitResultsByStatus,
                                                          Date asOfTime,
                                                          String sequencingProperty,
                                                          SequencingOrder sequencingOrder,
                                                          int pageSize) throws FunctionNotSupportedException {
        final String methodName = "findRelationshipsByProperty";
        IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.NO_RELATIONSHIP_PROPERTIES;
        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(repositoryName);
        throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                this.getClass().getName(),
                methodName,
                errorMessage,
                errorCode.getSystemAction(),
                errorCode.getUserAction());
    }

    /**
     * {@inheritDoc}
     */
    public List<Relationship> findRelationshipsByPropertyValue(String userId,
                                                               String relationshipTypeGUID,
                                                               String searchCriteria,
                                                               int fromRelationshipElement,
                                                               List<InstanceStatus> limitResultsByStatus,
                                                               Date asOfTime,
                                                               String sequencingProperty,
                                                               SequencingOrder sequencingOrder,
                                                               int pageSize) throws FunctionNotSupportedException {
        final String methodName = "findRelationshipsByPropertyName";
        IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.NO_RELATIONSHIP_PROPERTIES;
        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(repositoryName);
        throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                this.getClass().getName(),
                methodName,
                errorMessage,
                errorCode.getSystemAction(),
                errorCode.getUserAction());
    }

    /**
     * {@inheritDoc}
     */
    public EntityDetail addEntity(String userId,
                                  String entityTypeGUID,
                                  InstanceProperties initialProperties,
                                  List<Classification> initialClassifications,
                                  InstanceStatus initialStatus) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeErrorException,
            PropertyErrorException,
            ClassificationErrorException,
            StatusNotSupportedException,
            FunctionNotSupportedException,
            UserNotAuthorizedException {

        final String methodName = "addEntity";
        super.addEntityParameterValidation(userId,
                entityTypeGUID,
                initialProperties,
                initialClassifications,
                initialStatus,
                methodName);

        EntityDetail detail;
        String assetRid;
        String igcTypeName;
        String guid = null;

        // First ensure the TypeDef is mapped and that we support the requested status
        try {
            TypeDef typeDef = getTypeDefByGUID(userId, entityTypeGUID);
            HashSet<InstanceStatus> validStatuses = new HashSet<>(typeDef.getValidInstanceStatusList());
            if (!validStatuses.contains(initialStatus)) {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.UNSUPPORTED_STATUS;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        initialStatus.getName(),
                        entityTypeGUID,
                        repositoryName);
                throw new StatusNotSupportedException(errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            }
        } catch (TypeDefNotKnownException e) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    entityTypeGUID,
                    repositoryName);
            throw new TypeErrorException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        EntityMapping mapping = igcRepositoryHelper.getEntityMappingByGUID(entityTypeGUID);

        if (mapping == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    entityTypeGUID,
                    repositoryName);
            throw new TypeErrorException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else {
            igcTypeName = mapping.getIgcAssetType();
            // Next ensure that we actually support creation of this entity type
            if (!igcRestClient.isCreatableFromPOJO(igcTypeName)) {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CREATION_NOT_SUPPORTED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        entityTypeGUID,
                        igcTypeName,
                        repositoryName);
                throw new FunctionNotSupportedException(errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            } else {

                IGCCreate creationObj = new IGCCreate(igcTypeName);

                // Then ensure that we support all of the properties that we will attempt to set
                Set<String> mappedOmrsProperties = mapping.getWriteableMappedOmrsProperties();
                for (Map.Entry<String, InstancePropertyValue> entry : initialProperties.getInstanceProperties().entrySet()) {
                    String omrsPropertyName = entry.getKey();
                    InstancePropertyValue value = entry.getValue();
                    if (!mappedOmrsProperties.contains(omrsPropertyName)) {
                        IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CREATION_NOT_SUPPORTED;
                        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                                entityTypeGUID,
                                igcTypeName,
                                repositoryName);
                        throw new PropertyErrorException(errorCode.getHTTPErrorCode(),
                                this.getClass().getName(),
                                methodName,
                                errorMessage,
                                errorCode.getSystemAction(),
                                errorCode.getUserAction());
                    } else {
                        String igcPropertyName = mapping.getIgcPropertyName(omrsPropertyName);
                        creationObj.addProperty(igcPropertyName, AttributeMapping.getIgcValueFromPropertyValue(value));
                    }
                }

                // Then ensure that we support all of the classifications (and their properties) that we will attempt to set
                List<ClassificationMapping> classificationMappings = mapping.getClassificationMappers();
                HashMap<String, ClassificationMapping> omrsNameToMapping = new HashMap<>();
                for (ClassificationMapping classificationMapping : classificationMappings) {
                    String omrsClassification = classificationMapping.getOmrsClassificationType();
                    omrsNameToMapping.put(omrsClassification, classificationMapping);
                }
                for (Classification classification : initialClassifications) {
                    String omrsClassification = classification.getName();
                    if (!omrsNameToMapping.containsKey(omrsClassification)) {
                        IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CLASSIFICATION_NOT_APPLICABLE;
                        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                                omrsClassification,
                                igcTypeName);
                        throw new ClassificationErrorException(errorCode.getHTTPErrorCode(),
                                this.getClass().getName(),
                                methodName,
                                errorMessage,
                                errorCode.getSystemAction(),
                                errorCode.getUserAction());
                    }
                }

                // And FINALLY, if we support everything, go ahead with the creation
                assetRid = igcRestClient.create(creationObj);

                if (assetRid != null) {
                    guid = igcRepositoryHelper.getGuidForRid(assetRid);
                    // Then update the entity with the classifications (which cannot be set during creation)
                    List<String> classificationNames = new ArrayList<>();
                    try {
                        for (Classification classification : initialClassifications) {
                            classificationNames.add(classification.getName());
                            TypeDef classificationTypeDef = getTypeDefByGUID(userId, classification.getType().getTypeDefGUID());
                            igcRepositoryHelper.classifyEntity(userId, guid, classificationTypeDef, classification.getProperties());
                        }
                    } catch (TypeDefNotKnownException | EntityNotKnownException | RepositoryErrorException e) {
                        // If there is any failure, cleanup after ourselves by deleting the IGC asset that was created
                        igcRestClient.delete(assetRid);
                        IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CLASSIFICATION_ERROR_UNKNOWN;
                        String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                                classificationNames.toString(),
                                assetRid);
                        throw new ClassificationErrorException(errorCode.getHTTPErrorCode(),
                                this.getClass().getName(),
                                methodName,
                                errorMessage,
                                errorCode.getSystemAction(),
                                errorCode.getUserAction());
                    }
                }
            }
        }

        if (assetRid == null || guid == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CREATION_NOT_SUPPORTED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    entityTypeGUID,
                    igcTypeName,
                    repositoryName);
            throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else {
            try {
                detail = getEntityDetail(userId, guid);
            } catch (EntityNotKnownException e) {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.CREATION_NOT_SUPPORTED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        entityTypeGUID,
                        igcTypeName,
                        repositoryName);
                throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            }
        }

        return detail;

    }

    /**
     * {@inheritDoc}
     */
    public EntityDetail updateEntityProperties(String userId,
                                               String entityGUID,
                                               InstanceProperties properties) throws
            InvalidParameterException,
            RepositoryErrorException,
            EntityNotKnownException,
            PropertyErrorException,
            UserNotAuthorizedException {

        final String methodName = "updateEntityProperties";
        super.updateInstancePropertiesPropertyValidation(userId, entityGUID, properties, methodName);

        EntityDetail detail;

        String possiblyPrefixedRid = igcRepositoryHelper.getRidFromGuid(entityGUID);
        if (possiblyPrefixedRid == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    entityGUID,
                    "null",
                    repositoryName);
            throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        String rid = IGCRepositoryHelper.getRidFromGeneratedId(possiblyPrefixedRid);
        String prefix = IGCRepositoryHelper.getPrefixFromGeneratedId(possiblyPrefixedRid);
        Reference igcObj = igcRestClient.getAssetRefById(rid);

        if (igcObj == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    entityGUID,
                    rid,
                    repositoryName);
            throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        EntityMapping mapping = igcRepositoryHelper.getEntityMappingByIgcType(igcObj.getType(), prefix);

        if (mapping == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    entityGUID,
                    repositoryName);
            throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        } else {

            IGCUpdate updateObj = new IGCUpdate(rid);

            // Then ensure that we support all of the properties that we will attempt to set
            Set<String> mappedOmrsProperties = mapping.getWriteableMappedOmrsProperties();
            for (Map.Entry<String, InstancePropertyValue> entry : properties.getInstanceProperties().entrySet()) {
                String omrsPropertyName = entry.getKey();
                InstancePropertyValue value = entry.getValue();
                if (!mappedOmrsProperties.contains(omrsPropertyName)) {
                    IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ATTRIBUTE_TYPEDEF_NOT_MAPPED;
                    String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                            omrsPropertyName,
                            repositoryName);
                    throw new PropertyErrorException(errorCode.getHTTPErrorCode(),
                            this.getClass().getName(),
                            methodName,
                            errorMessage,
                            errorCode.getSystemAction(),
                            errorCode.getUserAction());
                } else {
                    String igcPropertyName = mapping.getIgcPropertyName(omrsPropertyName);
                    updateObj.addProperty(igcPropertyName, AttributeMapping.getIgcValueFromPropertyValue(value));
                }
            }

            if (!igcRestClient.update(updateObj)) {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.UPDATE_ERROR_UNKNOWN;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        updateObj.toString(),
                        entityGUID);
                throw new PropertyErrorException(errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction());
            } else {
                detail = igcRepositoryHelper.getEntityDetail(userId, entityGUID, igcObj);
            }

        }

        return detail;

    }

    /**
     * {@inheritDoc}
     */
    public void purgeEntity(String userId,
                            String typeDefGUID,
                            String typeDefName,
                            String deletedEntityGUID) throws
            InvalidParameterException,
            RepositoryErrorException,
            EntityNotKnownException,
            UserNotAuthorizedException {

        final String methodName = "purgeEntity";
        final String guidParameterName = "deletedEntityGUID";
        super.manageInstanceParameterValidation(userId,
                typeDefGUID,
                typeDefName,
                deletedEntityGUID,
                guidParameterName,
                methodName);

        String possiblyPrefixedRid = igcRepositoryHelper.getRidFromGuid(deletedEntityGUID);
        if (possiblyPrefixedRid == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    deletedEntityGUID,
                    "null",
                    repositoryName);
            throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        String rid = IGCRepositoryHelper.getRidFromGeneratedId(possiblyPrefixedRid);
        Reference igcObj = igcRestClient.getAssetRefById(rid);

        if (igcObj == null) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    deletedEntityGUID,
                    rid,
                    repositoryName);
            throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        if (!igcRestClient.delete(rid)) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.DELETE_ERROR_UNKNOWN;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    deletedEntityGUID,
                    rid);
            throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityDetail classifyEntity(String userId,
                                       String entityGUID,
                                       String classificationName,
                                       InstanceProperties classificationProperties) throws
            InvalidParameterException,
            RepositoryErrorException,
            EntityNotKnownException,
            ClassificationErrorException,
            PropertyErrorException,
            UserNotAuthorizedException {

        final String methodName = "classifyEntity";
        this.classifyEntityParameterValidation(
                userId,
                entityGUID,
                classificationName,
                classificationProperties,
                methodName
        );

        EntityDetail entityDetail;

        try {
            TypeDef classificationTypeDef = getTypeDefByName(userId, classificationName);
            if (classificationTypeDef != null) {
                Reference igcEntity = igcRepositoryHelper.classifyEntity(userId, entityGUID, classificationTypeDef, classificationProperties);
                entityDetail = igcRepositoryHelper.getEntityDetail(userId, entityGUID, igcEntity);
            } else {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        classificationName,
                        repositoryName);
                throw new ClassificationErrorException(
                        errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction()
                );
            }
        } catch (TypeDefNotKnownException e) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    classificationName,
                    repositoryName);
            throw new ClassificationErrorException(
                    errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction()
            );
        }

        return entityDetail;

    }

    /**
     * Remove a specific classification from an entity.
     *
     * @param userId unique identifier for requesting user.
     * @param entityGUID String unique identifier (guid) for the entity.
     * @param classificationName String name for the classification.
     * @return EntityDetail showing the resulting entity header, properties and classifications.
     * @throws InvalidParameterException one of the parameters is invalid or null.
     * @throws RepositoryErrorException there is a problem communicating with the metadata repository where
     *                                  the metadata collection is stored.
     * @throws EntityNotKnownException the entity identified by the guid is not found in the metadata collection
     * @throws ClassificationErrorException the requested classification is not set on the entity.
     * @throws UserNotAuthorizedException the userId is not permitted to perform this operation.
     */
    public EntityDetail declassifyEntity(String userId,
                                         String entityGUID,
                                         String classificationName) throws
            InvalidParameterException,
            RepositoryErrorException,
            EntityNotKnownException,
            ClassificationErrorException,
            UserNotAuthorizedException {

        final String methodName = "declassifyEntity";
        super.declassifyEntityParameterValidation(userId, entityGUID, classificationName);

        EntityDetail entityDetail;

        try {
            TypeDef classificationTypeDef = getTypeDefByName(userId, classificationName);
            if (classificationTypeDef != null) {
                Reference igcEntity = igcRepositoryHelper.declassifyEntity(userId, entityGUID, classificationTypeDef);
                entityDetail = igcRepositoryHelper.getEntityDetail(userId, entityGUID, igcEntity);
            } else {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        classificationName,
                        repositoryName);
                throw new ClassificationErrorException(
                        errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction()
                );
            }
        } catch (TypeDefNotKnownException e) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    classificationName,
                    repositoryName);
            throw new ClassificationErrorException(
                    errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction()
            );
        }

        return entityDetail;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Relationship addRelationship(String userId,
                                        String relationshipTypeGUID,
                                        InstanceProperties initialProperties,
                                        String entityOneGUID,
                                        String entityTwoGUID,
                                        InstanceStatus initialStatus) throws
            InvalidParameterException,
            RepositoryErrorException,
            TypeErrorException,
            PropertyErrorException,
            EntityNotKnownException,
            StatusNotSupportedException,
            UserNotAuthorizedException {

        final String methodName = "addRelationship";
        this.addRelationshipParameterValidation(
                userId,
                relationshipTypeGUID,
                initialProperties,
                entityOneGUID,
                entityTwoGUID,
                initialStatus,
                methodName
        );

        Relationship relationship;

        if (initialStatus != null && initialStatus != InstanceStatus.ACTIVE) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.UNSUPPORTED_STATUS;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    initialStatus.getName(),
                    relationshipTypeGUID,
                    repositoryName);
            throw new StatusNotSupportedException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        try {
            TypeDef relationshipTypeDef = getTypeDefByGUID(userId, relationshipTypeGUID);
            if (relationshipTypeDef != null) {

                String relationshipTypeName = relationshipTypeDef.getName();
                String entityOneRid = igcRepositoryHelper.getRidFromGuid(entityOneGUID);
                String entityTwoRid = igcRepositoryHelper.getRidFromGuid(entityTwoGUID);
                if (entityOneRid == null) {
                    IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
                    String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                            entityOneGUID,
                            "null",
                            repositoryName);
                    throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                            this.getClass().getName(),
                            methodName,
                            errorMessage,
                            errorCode.getSystemAction(),
                            errorCode.getUserAction());
                }
                if (entityTwoRid == null) {
                    IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
                    String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                            entityTwoGUID,
                            "null",
                            repositoryName);
                    throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                            this.getClass().getName(),
                            methodName,
                            errorMessage,
                            errorCode.getSystemAction(),
                            errorCode.getUserAction());
                }
                // TODO: do we need to handle potentially prefixed RIDs, because we are not at the moment?
                Reference entityOne = this.igcRestClient.getAssetRefById(entityOneRid);
                Reference entityTwo = this.igcRestClient.getAssetRefById(entityTwoRid);

                if (entityOne == null) {
                    IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
                    String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                            entityOneGUID,
                            entityOneRid,
                            repositoryName);
                    throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                            this.getClass().getName(),
                            methodName,
                            errorMessage,
                            errorCode.getSystemAction(),
                            errorCode.getUserAction());
                }
                if (entityTwo == null) {
                    IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.ENTITY_NOT_KNOWN;
                    String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                            entityTwoGUID,
                            entityTwoRid,
                            repositoryName);
                    throw new EntityNotKnownException(errorCode.getHTTPErrorCode(),
                            this.getClass().getName(),
                            methodName,
                            errorMessage,
                            errorCode.getSystemAction(),
                            errorCode.getUserAction());
                }

                RelationshipMapping relationshipMapping = igcRepositoryHelper.getRelationshipMappingByTypes(
                        relationshipTypeName,
                        entityOne.getType(),
                        entityTwo.getType()
                );

                if (relationshipMapping != null) {

                    relationship = RelationshipMapping.addIgcRelationship(
                            igcomrsRepositoryConnector,
                            relationshipMapping,
                            initialProperties,
                            entityOne,
                            entityTwo,
                            userId
                    );

                } else {
                    IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
                    String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                            relationshipTypeName,
                            repositoryName);
                    throw new TypeErrorException(
                            errorCode.getHTTPErrorCode(),
                            this.getClass().getName(),
                            methodName,
                            errorMessage,
                            errorCode.getSystemAction(),
                            errorCode.getUserAction()
                    );
                }

            } else {
                IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
                String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                        relationshipTypeGUID,
                        repositoryName);
                throw new TypeErrorException(
                        errorCode.getHTTPErrorCode(),
                        this.getClass().getName(),
                        methodName,
                        errorMessage,
                        errorCode.getSystemAction(),
                        errorCode.getUserAction()
                );
            }
        } catch (TypeDefNotKnownException e) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    relationshipTypeGUID,
                    repositoryName);
            throw new TypeErrorException(
                    errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction()
            );
        }

        return relationship;

    }

    /**
     * {@inheritDoc}
     */
    public void purgeRelationship(String userId,
                                  String typeDefGUID,
                                  String typeDefName,
                                  String deletedRelationshipGUID) throws
            InvalidParameterException,
            RepositoryErrorException,
            RelationshipNotKnownException,
            UserNotAuthorizedException {

        final String methodName = "purgeRelationship";
        final String relationshipParameterName = "deletedRelationshipGUID";
        super.manageInstanceParameterValidation(userId,
                typeDefGUID,
                typeDefName,
                deletedRelationshipGUID,
                relationshipParameterName,
                methodName);

        try {
            TypeDef relationshipTypeDef = getTypeDefByGUID(userId, typeDefGUID);
            if (relationshipTypeDef != null) {

                String relationshipTypeName = relationshipTypeDef.getName();
                String relationshipRid = igcRepositoryHelper.getRidFromGuid(deletedRelationshipGUID);

                if (relationshipRid == null) {
                    IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.RELATIONSHIP_NOT_KNOWN;
                    String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                            deletedRelationshipGUID,
                            repositoryName);
                    throw new RelationshipNotKnownException(errorCode.getHTTPErrorCode(),
                            this.getClass().getName(),
                            methodName,
                            errorMessage,
                            errorCode.getSystemAction(),
                            errorCode.getUserAction());
                }

                String relationshipType = RelationshipMapping.getRelationshipTypeFromRelationshipRID(relationshipRid);

                if (!relationshipType.equals(relationshipTypeName)) {
                    IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.RELATIONSHIP_NOT_KNOWN;
                    String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                            deletedRelationshipGUID,
                            repositoryName);
                    throw new RelationshipNotKnownException(errorCode.getHTTPErrorCode(),
                            this.getClass().getName(),
                            methodName,
                            errorMessage,
                            errorCode.getSystemAction(),
                            errorCode.getUserAction());
                }

                RelationshipMapping mapping = igcRepositoryHelper.getRelationshipMappingByGUID(typeDefGUID);

                // pull back the object on one end of the relationship
                String possiblyPrefixedOne = RelationshipMapping.getProxyOneRIDFromRelationshipRID(relationshipRid);
                String possiblyPrefixedTwo = RelationshipMapping.getProxyTwoRIDFromRelationshipRID(relationshipRid);
                String proxyOneRid = IGCRepositoryHelper.getRidFromGeneratedId(possiblyPrefixedOne);
                String proxyTwoRid = IGCRepositoryHelper.getRidFromGeneratedId(possiblyPrefixedTwo);
                RelationshipMapping.ProxyMapping proxyOneMapping = mapping.getProxyOneMapping();
                List<String> proxyOneRelationships = proxyOneMapping.getIgcRelationshipProperties();

                IGCSearchCondition forProxyOne = new IGCSearchCondition("_id", "=", proxyOneRid);
                IGCSearchConditionSet igcSearchConditionSet = new IGCSearchConditionSet(forProxyOne);

                IGCSearch igcSearch = new IGCSearch(proxyOneMapping.getIgcAssetType(), igcSearchConditionSet);
                igcSearch.addProperties(proxyOneRelationships);
                ReferenceList results = igcRestClient.search(igcSearch);

                // only proceed if there is actually anything to remove
                if (results != null && results.getPaging().getNumTotal() > 0) {

                    IGCUpdate igcUpdate = new IGCUpdate(proxyOneRid);
                    boolean bChanged = false;
                    Reference proxyOne = results.getItems().get(0);

                    String proxyOneType = proxyOne.getType();
                    List<String> pagedRelationships = igcRestClient.getPagedRelationalPropertiesFromPOJO(proxyOneType);

                    // null every property that holds the relationship, but retain the rest of the relationships that
                    // do not refer to the specific one we have been asked to purge
                    for (String relationshipAttr : proxyOneRelationships) {
                        if (pagedRelationships.contains(relationshipAttr)) {
                            ReferenceList relations = (ReferenceList) igcRestClient.getPropertyByName(proxyOne, relationshipAttr);
                            relations.getAllPages(igcRestClient);
                            for (Reference relation : relations.getItems()) {
                                String relationId = relation.getId();
                                if (relationId.equals(proxyTwoRid)) {
                                    bChanged = true;
                                    if (relations.getPaging().getNumTotal() == 1) {
                                        // If there is only this one relationship, we should explicitly null it
                                        igcUpdate.addRelationship(relationshipAttr, null);
                                    }
                                } else {
                                    igcUpdate.addRelationship(relationshipAttr, relationId);
                                }
                            }
                        } else {
                            // if it is an exclusive (not a paged) relationship attribute, null it directly (so long
                            // as it matches the one we are trying to delete)
                            Reference relation = (Reference) igcRestClient.getPropertyByName(proxyOne, relationshipAttr);
                            String relationId = relation.getId();
                            if (relationId.equals(proxyTwoRid)) {
                                bChanged = true;
                                igcUpdate.addRelationship(relationshipAttr, null);
                            }
                        }
                    }

                    if (bChanged) {
                        if (!igcRestClient.update(igcUpdate)) {
                            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.DELETE_RELATIONSHIP_ERROR_UNKNOWN;
                            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                                    deletedRelationshipGUID,
                                    proxyOneRid);
                            throw new RepositoryErrorException(errorCode.getHTTPErrorCode(),
                                    this.getClass().getName(),
                                    methodName,
                                    errorMessage,
                                    errorCode.getSystemAction(),
                                    errorCode.getUserAction());
                        }
                    }

                } else {
                    IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.RELATIONSHIP_NOT_KNOWN;
                    String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                            deletedRelationshipGUID,
                            repositoryName);
                    throw new RelationshipNotKnownException(errorCode.getHTTPErrorCode(),
                            this.getClass().getName(),
                            methodName,
                            errorMessage,
                            errorCode.getSystemAction(),
                            errorCode.getUserAction());
                }

            }
        } catch (TypeDefNotKnownException e) {
            IGCOMRSErrorCode errorCode = IGCOMRSErrorCode.TYPEDEF_NOT_MAPPED;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(
                    typeDefGUID,
                    repositoryName);
            throw new RelationshipNotKnownException(errorCode.getHTTPErrorCode(),
                    this.getClass().getName(),
                    methodName,
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction()
            );
        }

    }

    /**
     * Retrieves a mapping from attribute name to TypeDefAttribute for all OMRS attributes defined for the provided
     * OMRS TypeDef name.
     *
     * @param omrsTypeName the name of the OMRS TypeDef for which to retrieve the attributes
     * @return {@code Map<String, TypeDefAttribute>}
     */
    public Map<String, TypeDefAttribute> getTypeDefAttributesForType(String omrsTypeName) {
        return typeDefStore.getAllTypeDefAttributesForName(omrsTypeName);
    }

    /**
     * Retrieve the listing of implemented mappings that should be used for an entity search, including navigating
     * subtypes when a supertype is the entity type provided.
     *
     * @param entityTypeGUID the GUID of the OMRS entity type for which to search
     * @param userId the userId through which to search
     * @return {@code List<EntityMapping>}
     */
    private List<EntityMapping> getMappingsToSearch(String entityTypeGUID, String userId) throws
            InvalidParameterException,
            RepositoryErrorException {

        List<EntityMapping> mappingsToSearch = new ArrayList<>();

        // If no entityType was provided, add all implemented types (except Referenceable, as that could itself
        // include many objects that are not implemented)
        if (entityTypeGUID == null) {
            for (EntityMapping candidate : igcRepositoryHelper.getAllEntityMappings()) {
                if (!candidate.getOmrsTypeDefName().equals("Referenceable")) {
                    mappingsToSearch.add(candidate);
                }
            }
        } else {

            EntityMapping mappingExact = igcRepositoryHelper.getEntityMappingByGUID(entityTypeGUID);
            String requestedTypeName;
            // If no implemented mapping could be found, at least retrieve the TypeDef for further introspection
            // (so that if it has any implemented subtypes, we can still search for those)
            if (mappingExact == null) {
                TypeDef unimplemented = typeDefStore.getUnimplementedTypeDefByGUID(entityTypeGUID);
                requestedTypeName = unimplemented.getName();
            } else {
                requestedTypeName = mappingExact.getOmrsTypeDefName();
            }

            // Walk the hierarchy of types to ensure we search across all subtypes of the requested TypeDef as well
            List<TypeDef> allEntityTypes = findTypeDefsByCategory(userId, TypeDefCategory.ENTITY_DEF);

            for (TypeDef typeDef : allEntityTypes) {
                EntityMapping implementedMapping = igcRepositoryHelper.getEntityMappingByGUID(typeDef.getGUID());
                if (implementedMapping != null) {
                    if (repositoryHelper.isTypeOf(metadataCollectionId, typeDef.getName(), requestedTypeName)) {
                        // Add any subtypes of the requested type into the search
                        mappingsToSearch.add(implementedMapping);
                    }
                }
            }

        }

        return mappingsToSearch;

    }

}
