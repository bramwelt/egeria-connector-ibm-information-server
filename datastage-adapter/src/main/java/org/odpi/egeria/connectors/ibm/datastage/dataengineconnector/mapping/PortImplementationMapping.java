/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.mapping;

import org.odpi.egeria.connectors.ibm.datastage.dataengineconnector.model.DSJob;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.Reference;
import org.odpi.openmetadata.accessservices.dataengine.model.PortImplementation;
import org.odpi.openmetadata.accessservices.dataengine.model.PortType;

import java.util.List;

/**
 * Mappings for creating a PortImplementation.
 */
public class PortImplementationMapping extends BaseMapping {

    private PortImplementation portImplementation;

    /**
     * Creates a PortImplementation for the provided job and link information.
     *
     * @param job the job for which to create the PortImplementation
     * @param link the link containing details for the PortImplementation
     * @param portType the port type to use
     * @param stageNameSuffix the unique suffix (based on the stage name) to ensure each schema is unique
     */
    public PortImplementationMapping(DSJob job, Reference link, PortType portType, String stageNameSuffix) {
        super(job.getIgcRestClient());
        portImplementation = null;
        if (link != null) {
            portImplementation = new PortImplementation();
            portImplementation.setQualifiedName(getFullyQualifiedName(link) + stageNameSuffix);
            portImplementation.setDisplayName(link.getName());
            portImplementation.setPortType(portType);
            SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(job, link, stageNameSuffix);
            portImplementation.setSchemaType(schemaTypeMapping.getSchemaType());
        }
    }

    /**
     * Creates a PortImplementation for the provided job and data store field information, for the given stage.
     *
     * @param job the job for which to create the PortImplementation
     * @param stage the stage for which to create the PortImplementation
     * @param portType the port type to use
     * @param fields the data store fields from which to create the PortImplementation's schema
     * @param fullyQualifiedStageName the qualified name of the stage to ensure each schema is unique
     */
    public PortImplementationMapping(DSJob job, Reference stage, PortType portType, List<Reference> fields, String fullyQualifiedStageName) {
        super(job.getIgcRestClient());
        portImplementation = null;
        if (stage != null && fields != null && !fields.isEmpty()) {
            portImplementation = new PortImplementation();
            String storeQualifiedName = getParentQualifiedName(fields.get(0));
            String storeName = getParentDisplayName(fields.get(0));
            portImplementation.setQualifiedName(storeQualifiedName + fullyQualifiedStageName);
            portImplementation.setDisplayName(storeName);
            portImplementation.setPortType(portType);
            SchemaTypeMapping schemaTypeMapping = new SchemaTypeMapping(job, stage, storeName, storeQualifiedName, fields, fullyQualifiedStageName);
            portImplementation.setSchemaType(schemaTypeMapping.getSchemaType());
        }
    }

    /**
     * Retrieve the PortImplementation that was setup.
     *
     * @return PortImplementation
     */
    public PortImplementation getPortImplementation() { return portImplementation; }

}
