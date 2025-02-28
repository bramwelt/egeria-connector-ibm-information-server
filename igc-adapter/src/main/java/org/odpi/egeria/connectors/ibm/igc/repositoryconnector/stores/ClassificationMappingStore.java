/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.repositoryconnector.stores;

import org.odpi.egeria.connectors.ibm.igc.clientlibrary.IGCVersionEnum;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.IGCOMRSRepositoryConnector;
import org.odpi.egeria.connectors.ibm.igc.repositoryconnector.mapping.classifications.ClassificationMapping;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Store of implemented classification mappings for the repository.
 */
public class ClassificationMappingStore {

    private static final Logger log = LoggerFactory.getLogger(ClassificationMappingStore.class);

    private IGCOMRSRepositoryConnector igcomrsRepositoryConnector;

    private List<TypeDef> typeDefs;

    private Map<String, ClassificationMapping> omrsGuidToMapping;
    private Map<String, String> omrsNameToGuid;

    public ClassificationMappingStore(IGCOMRSRepositoryConnector igcomrsRepositoryConnector) {
        typeDefs = new ArrayList<>();
        omrsGuidToMapping = new HashMap<>();
        omrsNameToGuid = new HashMap<>();
        this.igcomrsRepositoryConnector = igcomrsRepositoryConnector;
    }

    /**
     * Adds a classification mapping for the provided TypeDef, using the provided Java class for the mapping.
     *
     * @param omrsTypeDef the OMRS TypeDef
     * @param mappingClass the ClassificationMapping Java class
     * @return boolean false when unable to retrieve ClassificationMapping from provided class
     */
    public boolean addMapping(TypeDef omrsTypeDef, Class mappingClass) {

        ClassificationMapping mapping = getClassificationMapper(mappingClass);

        if (mapping != null) {
            typeDefs.add(omrsTypeDef);
            String guid = omrsTypeDef.getGUID();
            String name = omrsTypeDef.getName();
            omrsGuidToMapping.put(guid, mapping);
            omrsNameToGuid.put(name, guid);
        }

        return (mapping != null);

    }

    /**
     * Retrieves the listing of all TypeDefs for which classification mappings are implemented.
     *
     * @return {@code List<TypeDef>}
     */
    public List<TypeDef> getTypeDefs() { return this.typeDefs; }

    /**
     * Retrieves a classification mapping based on the GUID of the OMRS classification type.
     *
     * @param guid of the OMRS classification type
     * @return ClassificationMapping
     */
    public ClassificationMapping getMappingByOmrsTypeGUID(String guid) {
        if (omrsGuidToMapping.containsKey(guid)) {
            return omrsGuidToMapping.get(guid);
        } else {
            if (log.isWarnEnabled()) { log.warn("Unable to find mapping for OMRS type: {}", guid); }
            return null;
        }
    }

    /**
     * Retrieves a classification mapping based on the name of the OMRS classification type.
     *
     * @param name of the OMRS classification type
     * @return ClassificationMapping
     */
    public ClassificationMapping getMappingByOmrsTypeName(String name) {
        if (omrsNameToGuid.containsKey(name)) {
            return getMappingByOmrsTypeGUID(omrsNameToGuid.get(name));
        } else {
            if (log.isWarnEnabled()) { log.warn("Unable to find mapping for OMRS type: {}", name); }
            return null;
        }
    }

    /**
     * Retrieves a ClassificationMapping by OMRS classification type from those that are listed as implemented.
     *
     * @param omrsClassificationType the name of the OMRS classification type for which to retrieve a mapping
     * @param igcAssetType the IGC asset type
     * @return ClassificationMapping
     */
    public ClassificationMapping getMappingByTypes(String omrsClassificationType,
                                                   String igcAssetType) {
        ClassificationMapping found = null;
        ClassificationMapping candidate = getMappingByOmrsTypeName(omrsClassificationType);
        String candidateIgcType = candidate.getIgcAssetType();
        Set<String> excludedIgcTypes = candidate.getExcludedIgcAssetTypes();
        if (!excludedIgcTypes.contains(igcAssetType)) {
            // If the IGC types also match, short-circuit out
            if (candidateIgcType.equals(igcAssetType) || candidateIgcType.equals("main_object")) {
                found = candidate;
            } else if (candidate.hasSubTypes()) {
                // Otherwise, check any sub-types and short-circuit out if we find a match
                for (ClassificationMapping subMapping : candidate.getSubTypes()) {
                    candidateIgcType = candidate.getIgcAssetType();
                    if (candidateIgcType.equals(igcAssetType) || candidateIgcType.equals("main_object")) {
                        found = subMapping;
                        break;
                    }
                }
            }
        }
        return found;
    }

    /**
     * Introspect a mapping class to retrieve a ClassificationMapping.
     *
     * @param mappingClass the mapping class to retrieve an instance of
     * @return ClassificationMapping
     */
    private ClassificationMapping getClassificationMapper(Class mappingClass) {
        ClassificationMapping classificationMapper = null;
        try {
            Method getInstance = mappingClass.getMethod("getInstance", IGCVersionEnum.class);
            classificationMapper = (ClassificationMapping) getInstance.invoke(null, igcomrsRepositoryConnector.getIGCVersion());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            if (log.isErrorEnabled()) { log.error("Unable to find or instantiate ClassificationMapping class: {}", mappingClass, e); }
        }
        return classificationMapper;
    }

}
