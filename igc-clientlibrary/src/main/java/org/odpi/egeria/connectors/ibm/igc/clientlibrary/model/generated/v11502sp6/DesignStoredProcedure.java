/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.generated.v11502sp6;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.annotation.Generated;
import org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.common.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * POJO for the {@code design_stored_procedure} asset type in IGC, displayed as '{@literal Design Stored Procedure}' in the IGC UI.
 * <br><br>
 * (this code has been generated based on out-of-the-box IGC metadata types;
 *  if modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@Generated("org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.IGCRestModelGenerator")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("design_stored_procedure")
public class DesignStoredProcedure extends Reference {

    public static String getIgcTypeDisplayName() { return "Design Stored Procedure"; }

    /**
     * The {@code name} property, displayed as '{@literal Name}' in the IGC UI.
     */
    protected String name;

    /**
     * The {@code short_description} property, displayed as '{@literal Short Description}' in the IGC UI.
     */
    protected String short_description;

    /**
     * The {@code long_description} property, displayed as '{@literal Long Description}' in the IGC UI.
     */
    protected String long_description;

    /**
     * The {@code physical_data_model} property, displayed as '{@literal Physical Data Model}' in the IGC UI.
     * <br><br>
     * Will be a single {@link Reference} to a {@link PhysicalDataModel} object.
     */
    protected Reference physical_data_model;

    /**
     * The {@code labels} property, displayed as '{@literal Labels}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link Label} objects.
     */
    protected ReferenceList labels;

    /**
     * The {@code stewards} property, displayed as '{@literal Stewards}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link AsclSteward} objects.
     */
    protected ReferenceList stewards;

    /**
     * The {@code assigned_to_terms} property, displayed as '{@literal Assigned to Terms}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link Term} objects.
     */
    protected ReferenceList assigned_to_terms;

    /**
     * The {@code implements_rules} property, displayed as '{@literal Implements Rules}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link InformationGovernanceRule} objects.
     */
    protected ReferenceList implements_rules;

    /**
     * The {@code governed_by_rules} property, displayed as '{@literal Governed by Rules}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link InformationGovernanceRule} objects.
     */
    protected ReferenceList governed_by_rules;

    /**
     * The {@code implemented_by_stored_procedures} property, displayed as '{@literal Implemented by Stored Procedures}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link StoredProcedure} objects.
     */
    protected ReferenceList implemented_by_stored_procedures;

    /**
     * The {@code design_stored_procedure_parameters} property, displayed as '{@literal Design Stored Procedure Parameters}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DesignStoredProcedureParameter} objects.
     */
    protected ReferenceList design_stored_procedure_parameters;

    /**
     * The {@code alias_(business_name)} property, displayed as '{@literal Alias (Business Name)}' in the IGC UI.
     */
    @JsonProperty("alias_(business_name)") protected String alias__business_name_;

    /**
     * The {@code name_qualifier} property, displayed as '{@literal Name Qualifier}' in the IGC UI.
     */
    protected String name_qualifier;

    /**
     * The {@code source_code} property, displayed as '{@literal Source Code}' in the IGC UI.
     */
    protected String source_code;

    /**
     * The {@code error_code} property, displayed as '{@literal Error Code}' in the IGC UI.
     */
    protected String error_code;

    /**
     * The {@code imported_from} property, displayed as '{@literal Imported From}' in the IGC UI.
     */
    protected String imported_from;

    /**
     * The {@code blueprint_elements} property, displayed as '{@literal Blueprint Elements}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link BlueprintElementLink} objects.
     */
    protected ReferenceList blueprint_elements;

    /**
     * The {@code in_collections} property, displayed as '{@literal In Collections}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link Collection} objects.
     */
    protected ReferenceList in_collections;

    /**
     * The {@code created_by} property, displayed as '{@literal Created By}' in the IGC UI.
     */
    protected String created_by;

    /**
     * The {@code created_on} property, displayed as '{@literal Created On}' in the IGC UI.
     */
    protected Date created_on;

    /**
     * The {@code modified_by} property, displayed as '{@literal Modified By}' in the IGC UI.
     */
    protected String modified_by;

    /**
     * The {@code modified_on} property, displayed as '{@literal Modified On}' in the IGC UI.
     */
    protected Date modified_on;


    /** @see #name */ @JsonProperty("name")  public String getTheName() { return this.name; }
    /** @see #name */ @JsonProperty("name")  public void setTheName(String name) { this.name = name; }

    /** @see #short_description */ @JsonProperty("short_description")  public String getShortDescription() { return this.short_description; }
    /** @see #short_description */ @JsonProperty("short_description")  public void setShortDescription(String short_description) { this.short_description = short_description; }

    /** @see #long_description */ @JsonProperty("long_description")  public String getLongDescription() { return this.long_description; }
    /** @see #long_description */ @JsonProperty("long_description")  public void setLongDescription(String long_description) { this.long_description = long_description; }

    /** @see #physical_data_model */ @JsonProperty("physical_data_model")  public Reference getPhysicalDataModel() { return this.physical_data_model; }
    /** @see #physical_data_model */ @JsonProperty("physical_data_model")  public void setPhysicalDataModel(Reference physical_data_model) { this.physical_data_model = physical_data_model; }

    /** @see #labels */ @JsonProperty("labels")  public ReferenceList getLabels() { return this.labels; }
    /** @see #labels */ @JsonProperty("labels")  public void setLabels(ReferenceList labels) { this.labels = labels; }

    /** @see #stewards */ @JsonProperty("stewards")  public ReferenceList getStewards() { return this.stewards; }
    /** @see #stewards */ @JsonProperty("stewards")  public void setStewards(ReferenceList stewards) { this.stewards = stewards; }

    /** @see #assigned_to_terms */ @JsonProperty("assigned_to_terms")  public ReferenceList getAssignedToTerms() { return this.assigned_to_terms; }
    /** @see #assigned_to_terms */ @JsonProperty("assigned_to_terms")  public void setAssignedToTerms(ReferenceList assigned_to_terms) { this.assigned_to_terms = assigned_to_terms; }

    /** @see #implements_rules */ @JsonProperty("implements_rules")  public ReferenceList getImplementsRules() { return this.implements_rules; }
    /** @see #implements_rules */ @JsonProperty("implements_rules")  public void setImplementsRules(ReferenceList implements_rules) { this.implements_rules = implements_rules; }

    /** @see #governed_by_rules */ @JsonProperty("governed_by_rules")  public ReferenceList getGovernedByRules() { return this.governed_by_rules; }
    /** @see #governed_by_rules */ @JsonProperty("governed_by_rules")  public void setGovernedByRules(ReferenceList governed_by_rules) { this.governed_by_rules = governed_by_rules; }

    /** @see #implemented_by_stored_procedures */ @JsonProperty("implemented_by_stored_procedures")  public ReferenceList getImplementedByStoredProcedures() { return this.implemented_by_stored_procedures; }
    /** @see #implemented_by_stored_procedures */ @JsonProperty("implemented_by_stored_procedures")  public void setImplementedByStoredProcedures(ReferenceList implemented_by_stored_procedures) { this.implemented_by_stored_procedures = implemented_by_stored_procedures; }

    /** @see #design_stored_procedure_parameters */ @JsonProperty("design_stored_procedure_parameters")  public ReferenceList getDesignStoredProcedureParameters() { return this.design_stored_procedure_parameters; }
    /** @see #design_stored_procedure_parameters */ @JsonProperty("design_stored_procedure_parameters")  public void setDesignStoredProcedureParameters(ReferenceList design_stored_procedure_parameters) { this.design_stored_procedure_parameters = design_stored_procedure_parameters; }

    /** @see #alias__business_name_ */ @JsonProperty("alias_(business_name)")  public String getAliasBusinessName() { return this.alias__business_name_; }
    /** @see #alias__business_name_ */ @JsonProperty("alias_(business_name)")  public void setAliasBusinessName(String alias__business_name_) { this.alias__business_name_ = alias__business_name_; }

    /** @see #name_qualifier */ @JsonProperty("name_qualifier")  public String getNameQualifier() { return this.name_qualifier; }
    /** @see #name_qualifier */ @JsonProperty("name_qualifier")  public void setNameQualifier(String name_qualifier) { this.name_qualifier = name_qualifier; }

    /** @see #source_code */ @JsonProperty("source_code")  public String getSourceCode() { return this.source_code; }
    /** @see #source_code */ @JsonProperty("source_code")  public void setSourceCode(String source_code) { this.source_code = source_code; }

    /** @see #error_code */ @JsonProperty("error_code")  public String getErrorCode() { return this.error_code; }
    /** @see #error_code */ @JsonProperty("error_code")  public void setErrorCode(String error_code) { this.error_code = error_code; }

    /** @see #imported_from */ @JsonProperty("imported_from")  public String getImportedFrom() { return this.imported_from; }
    /** @see #imported_from */ @JsonProperty("imported_from")  public void setImportedFrom(String imported_from) { this.imported_from = imported_from; }

    /** @see #blueprint_elements */ @JsonProperty("blueprint_elements")  public ReferenceList getBlueprintElements() { return this.blueprint_elements; }
    /** @see #blueprint_elements */ @JsonProperty("blueprint_elements")  public void setBlueprintElements(ReferenceList blueprint_elements) { this.blueprint_elements = blueprint_elements; }

    /** @see #in_collections */ @JsonProperty("in_collections")  public ReferenceList getInCollections() { return this.in_collections; }
    /** @see #in_collections */ @JsonProperty("in_collections")  public void setInCollections(ReferenceList in_collections) { this.in_collections = in_collections; }

    /** @see #created_by */ @JsonProperty("created_by")  public String getCreatedBy() { return this.created_by; }
    /** @see #created_by */ @JsonProperty("created_by")  public void setCreatedBy(String created_by) { this.created_by = created_by; }

    /** @see #created_on */ @JsonProperty("created_on")  public Date getCreatedOn() { return this.created_on; }
    /** @see #created_on */ @JsonProperty("created_on")  public void setCreatedOn(Date created_on) { this.created_on = created_on; }

    /** @see #modified_by */ @JsonProperty("modified_by")  public String getModifiedBy() { return this.modified_by; }
    /** @see #modified_by */ @JsonProperty("modified_by")  public void setModifiedBy(String modified_by) { this.modified_by = modified_by; }

    /** @see #modified_on */ @JsonProperty("modified_on")  public Date getModifiedOn() { return this.modified_on; }
    /** @see #modified_on */ @JsonProperty("modified_on")  public void setModifiedOn(Date modified_on) { this.modified_on = modified_on; }

    public static Boolean canBeCreated() { return false; }
    public static Boolean includesModificationDetails() { return true; }
    private static final List<String> NON_RELATIONAL_PROPERTIES = Arrays.asList(
        "name",
        "short_description",
        "long_description",
        "alias_(business_name)",
        "name_qualifier",
        "source_code",
        "error_code",
        "imported_from",
        "created_by",
        "created_on",
        "modified_by",
        "modified_on"
    );
    private static final List<String> STRING_PROPERTIES = Arrays.asList(
        "name",
        "short_description",
        "long_description",
        "alias_(business_name)",
        "name_qualifier",
        "source_code",
        "error_code",
        "imported_from",
        "created_by",
        "modified_by"
    );
    private static final List<String> PAGED_RELATIONAL_PROPERTIES = Arrays.asList(
        "labels",
        "stewards",
        "assigned_to_terms",
        "implements_rules",
        "governed_by_rules",
        "implemented_by_stored_procedures",
        "design_stored_procedure_parameters",
        "blueprint_elements",
        "in_collections"
    );
    private static final List<String> ALL_PROPERTIES = Arrays.asList(
        "name",
        "short_description",
        "long_description",
        "physical_data_model",
        "labels",
        "stewards",
        "assigned_to_terms",
        "implements_rules",
        "governed_by_rules",
        "implemented_by_stored_procedures",
        "design_stored_procedure_parameters",
        "alias_(business_name)",
        "name_qualifier",
        "source_code",
        "error_code",
        "imported_from",
        "blueprint_elements",
        "in_collections",
        "created_by",
        "created_on",
        "modified_by",
        "modified_on"
    );
    public static List<String> getNonRelationshipProperties() { return NON_RELATIONAL_PROPERTIES; }
    public static List<String> getStringProperties() { return STRING_PROPERTIES; }
    public static List<String> getPagedRelationshipProperties() { return PAGED_RELATIONAL_PROPERTIES; }
    public static List<String> getAllProperties() { return ALL_PROPERTIES; }
    public static Boolean isDesignStoredProcedure(Object obj) { return (obj.getClass() == DesignStoredProcedure.class); }

}
