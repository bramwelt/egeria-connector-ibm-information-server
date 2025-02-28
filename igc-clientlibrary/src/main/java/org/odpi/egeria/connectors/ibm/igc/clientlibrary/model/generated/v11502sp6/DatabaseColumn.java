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
 * POJO for the {@code database_column} asset type in IGC, displayed as '{@literal Database Column}' in the IGC UI.
 * <br><br>
 * (this code has been generated based on out-of-the-box IGC metadata types;
 *  if modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@Generated("org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.IGCRestModelGenerator")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("database_column")
public class DatabaseColumn extends Reference {

    public static String getIgcTypeDisplayName() { return "Database Column"; }

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
     * The {@code database_table_or_view} property, displayed as '{@literal Database Table or View}' in the IGC UI.
     * <br><br>
     * Will be a single {@link Reference} to a {@link Datagroup} object.
     */
    protected Reference database_table_or_view;

    /**
     * The {@code qualityScore} property, displayed as '{@literal Quality Score}' in the IGC UI.
     */
    protected String qualityScore;

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
     * The {@code database_table} property, displayed as '{@literal Database Table}' in the IGC UI.
     * <br><br>
     * Will be a single {@link Reference} to a {@link DatabaseTable} object.
     */
    protected Reference database_table;

    /**
     * The {@code view} property, displayed as '{@literal Database View}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link View} objects.
     */
    protected ReferenceList view;

    /**
     * The {@code database_alias} property, displayed as '{@literal Database Alias}' in the IGC UI.
     * <br><br>
     * Will be a single {@link Reference} to a {@link DatabaseAlias} object.
     */
    protected Reference database_alias;

    /**
     * The {@code implements_entity_attributes} property, displayed as '{@literal Implements Entity Attributes}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link EntityAttribute} objects.
     */
    protected ReferenceList implements_entity_attributes;

    /**
     * The {@code implements_design_columns} property, displayed as '{@literal Implements Design Columns}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DesignColumn} objects.
     */
    protected ReferenceList implements_design_columns;

    /**
     * The {@code type} property, displayed as '{@literal Native Type}' in the IGC UI.
     */
    protected String type;

    /**
     * The {@code odbc_type} property, displayed as '{@literal ODBC Type}' in the IGC UI.
     * <br><br>
     * Can be one of the following values:
     * <ul>
     *     <li>CHAR (displayed in the UI as 'CHAR')</li>
     *     <li>VARCHAR (displayed in the UI as 'VARCHAR')</li>
     *     <li>LONGVARCHAR (displayed in the UI as 'LONGVARCHAR')</li>
     *     <li>WCHAR (displayed in the UI as 'WCHAR')</li>
     *     <li>WVARCHAR (displayed in the UI as 'WVARCHAR')</li>
     *     <li>WLONGVARCHAR (displayed in the UI as 'WLONGVARCHAR')</li>
     *     <li>DECIMAL (displayed in the UI as 'DECIMAL')</li>
     *     <li>NUMERIC (displayed in the UI as 'NUMERIC')</li>
     *     <li>SMALLINT (displayed in the UI as 'SMALLINT')</li>
     *     <li>INTEGER (displayed in the UI as 'INTEGER')</li>
     *     <li>REAL (displayed in the UI as 'REAL')</li>
     *     <li>FLOAT (displayed in the UI as 'FLOAT')</li>
     *     <li>DOUBLE (displayed in the UI as 'DOUBLE')</li>
     *     <li>BIT (displayed in the UI as 'BIT')</li>
     *     <li>TINYINT (displayed in the UI as 'TINYINT')</li>
     *     <li>BIGINT (displayed in the UI as 'BIGINT')</li>
     *     <li>BINARY (displayed in the UI as 'BINARY')</li>
     *     <li>VARBINARY (displayed in the UI as 'VARBINARY')</li>
     *     <li>LONGVARBINARY (displayed in the UI as 'LONGVARBINARY')</li>
     *     <li>DATE (displayed in the UI as 'DATE')</li>
     *     <li>TIME (displayed in the UI as 'TIME')</li>
     *     <li>TIMESTAMP (displayed in the UI as 'TIMESTAMP')</li>
     *     <li>GUID (displayed in the UI as 'GUID')</li>
     *     <li>UNKNOWN (displayed in the UI as 'UNKNOWN')</li>
     * </ul>
     */
    protected String odbc_type;

    /**
     * The {@code data_type} property, displayed as '{@literal Data Type}' in the IGC UI.
     * <br><br>
     * Can be one of the following values:
     * <ul>
     *     <li>INT8 (displayed in the UI as 'INT8')</li>
     *     <li>INT16 (displayed in the UI as 'INT16')</li>
     *     <li>INT32 (displayed in the UI as 'INT32')</li>
     *     <li>INT64 (displayed in the UI as 'INT64')</li>
     *     <li>SFLOAT (displayed in the UI as 'SFLOAT')</li>
     *     <li>DFLOAT (displayed in the UI as 'DFLOAT')</li>
     *     <li>QFLOAT (displayed in the UI as 'QFLOAT')</li>
     *     <li>DECIMAL (displayed in the UI as 'DECIMAL')</li>
     *     <li>STRING (displayed in the UI as 'STRING')</li>
     *     <li>BINARY (displayed in the UI as 'BINARY')</li>
     *     <li>BOOLEAN (displayed in the UI as 'BOOLEAN')</li>
     *     <li>DATE (displayed in the UI as 'DATE')</li>
     *     <li>TIME (displayed in the UI as 'TIME')</li>
     *     <li>DATETIME (displayed in the UI as 'DATETIME')</li>
     *     <li>DURATION (displayed in the UI as 'DURATION')</li>
     *     <li>CHOICE (displayed in the UI as 'CHOICE')</li>
     *     <li>ORDERED_GROUP (displayed in the UI as 'ORDERED_GROUP')</li>
     *     <li>UNORDERED_GROUP (displayed in the UI as 'UNORDERED_GROUP')</li>
     *     <li>GUID (displayed in the UI as 'GUID')</li>
     *     <li>UNKNOWN (displayed in the UI as 'UNKNOWN')</li>
     *     <li>JSON (displayed in the UI as 'JSON')</li>
     *     <li>XML (displayed in the UI as 'XML')</li>
     * </ul>
     */
    protected String data_type;

    /**
     * The {@code default_value} property, displayed as '{@literal Default Value}' in the IGC UI.
     */
    protected String default_value;

    /**
     * The {@code database_domains} property, displayed as '{@literal Database Domains}' in the IGC UI.
     * <br><br>
     * Will be a single {@link Reference} to a {@link DatabaseDomain} object.
     */
    protected Reference database_domains;

    /**
     * The {@code referenced_by_database_columns} property, displayed as '{@literal Referenced by Database Columns}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DatabaseColumn} objects.
     */
    protected ReferenceList referenced_by_database_columns;

    /**
     * The {@code selected_classification} property, displayed as '{@literal Selected Data Classification}' in the IGC UI.
     * <br><br>
     * Will be a single {@link Reference} to a {@link DataClass} object.
     */
    protected Reference selected_classification;

    /**
     * The {@code detected_classifications} property, displayed as '{@literal Detected Data Classifications}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link Classification} objects.
     */
    protected ReferenceList detected_classifications;

    /**
     * The {@code length} property, displayed as '{@literal Length}' in the IGC UI.
     */
    protected String length;

    /**
     * The {@code minimum_length} property, displayed as '{@literal Minimum Length}' in the IGC UI.
     */
    protected Number minimum_length;

    /**
     * The {@code fraction} property, displayed as '{@literal Fraction}' in the IGC UI.
     */
    protected Number fraction;

    /**
     * The {@code position} property, displayed as '{@literal Position}' in the IGC UI.
     */
    protected Number position;

    /**
     * The {@code level} property, displayed as '{@literal Level}' in the IGC UI.
     */
    protected Number level;

    /**
     * The {@code occurs} property, displayed as '{@literal Occurs}' in the IGC UI.
     */
    protected ArrayList<String> occurs;

    /**
     * The {@code start_end_columns} property, displayed as '{@literal Starting .. Ending Columns}' in the IGC UI.
     */
    protected String start_end_columns;

    /**
     * The {@code allows_null_values} property, displayed as '{@literal Allow Null Values}' in the IGC UI.
     */
    protected Boolean allows_null_values;

    /**
     * The {@code unique} property, displayed as '{@literal Unique Constraint}' in the IGC UI.
     */
    protected Boolean unique;

    /**
     * The {@code same_as_data_sources} property, displayed as '{@literal Same as Data Sources}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DataItem} objects.
     */
    protected ReferenceList same_as_data_sources;

    /**
     * The {@code references_database_columns} property, displayed as '{@literal References Database Columns}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DatabaseColumn} objects.
     */
    protected ReferenceList references_database_columns;

    /**
     * The {@code defined_primary_key} property, displayed as '{@literal Defined Primary Key}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link CandidateKey} objects.
     */
    protected ReferenceList defined_primary_key;

    /**
     * The {@code defined_non_primary_key} property, displayed as '{@literal Defined Non Primary Key}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link CandidateKey} objects.
     */
    protected ReferenceList defined_non_primary_key;

    /**
     * The {@code implemented_key} property, displayed as '{@literal Implemented Key}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link CandidateKey} objects.
     */
    protected ReferenceList implemented_key;

    /**
     * The {@code selected_primary_key} property, displayed as '{@literal User Selected Primary Key}' in the IGC UI.
     */
    protected Boolean selected_primary_key;

    /**
     * The {@code selected_natural_key} property, displayed as '{@literal User Selected Natural Key}' in the IGC UI.
     */
    protected Boolean selected_natural_key;

    /**
     * The {@code defined_foreign_key} property, displayed as '{@literal Defined Foreign Key}' in the IGC UI.
     */
    protected Boolean defined_foreign_key;

    /**
     * The {@code defined_foreign_key_references} property, displayed as '{@literal Defined Foreign Key References}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DatabaseColumn} objects.
     */
    protected ReferenceList defined_foreign_key_references;

    /**
     * The {@code defined_foreign_key_referenced} property, displayed as '{@literal Defined Foreign Key Referenced}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DataItem} objects.
     */
    protected ReferenceList defined_foreign_key_referenced;

    /**
     * The {@code selected_foreign_key} property, displayed as '{@literal User Selected Foreign Key}' in the IGC UI.
     */
    protected Boolean selected_foreign_key;

    /**
     * The {@code selected_foreign_key_references} property, displayed as '{@literal User Selected Foreign Key References}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DatabaseColumn} objects.
     */
    protected ReferenceList selected_foreign_key_references;

    /**
     * The {@code selected_foreign_key_referenced} property, displayed as '{@literal User Selected Foreign Key Referenced}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DatabaseColumn} objects.
     */
    protected ReferenceList selected_foreign_key_referenced;

    /**
     * The {@code database_indexes} property, displayed as '{@literal Index}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DatabaseIndex} objects.
     */
    protected ReferenceList database_indexes;

    /**
     * The {@code index} property, displayed as '{@literal Index}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DatabaseIndex} objects.
     */
    protected ReferenceList index;

    /**
     * The {@code validity_tables} property, displayed as '{@literal Validity Tables}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link ValidityTable} objects.
     */
    protected ReferenceList validity_tables;

    /**
     * The {@code uniqueFlag} property, displayed as '{@literal Require Unique Values}' in the IGC UI.
     */
    protected Boolean uniqueFlag;

    /**
     * The {@code nullabilityFlag} property, displayed as '{@literal Include Null Values}' in the IGC UI.
     */
    protected Boolean nullabilityFlag;

    /**
     * The {@code constantFlag} property, displayed as '{@literal Include Constant Values}' in the IGC UI.
     */
    protected Boolean constantFlag;

    /**
     * The {@code domainType} property, displayed as '{@literal Domain}' in the IGC UI.
     */
    protected ArrayList<String> domainType;

    /**
     * The {@code numberCompleteValues} property, displayed as '{@literal Number of Complete Values}' in the IGC UI.
     */
    protected ArrayList<Number> numberCompleteValues;

    /**
     * The {@code numberValidValues} property, displayed as '{@literal Number of Valid Values}' in the IGC UI.
     */
    protected ArrayList<Number> numberValidValues;

    /**
     * The {@code numberEmptyValues} property, displayed as '{@literal Number of Empty Values}' in the IGC UI.
     */
    protected ArrayList<Number> numberEmptyValues;

    /**
     * The {@code numberNullValues} property, displayed as '{@literal Number of Null Values}' in the IGC UI.
     */
    protected ArrayList<Number> numberNullValues;

    /**
     * The {@code numberDistinctValues} property, displayed as '{@literal Number of Distinct Values}' in the IGC UI.
     */
    protected ArrayList<Number> numberDistinctValues;

    /**
     * The {@code numberFormats} property, displayed as '{@literal Number of Distinct Formats}' in the IGC UI.
     */
    protected ArrayList<Number> numberFormats;

    /**
     * The {@code numberZeroValues} property, displayed as '{@literal Number of Zero Values}' in the IGC UI.
     */
    protected ArrayList<Number> numberZeroValues;

    /**
     * The {@code inferredDataType} property, displayed as '{@literal Inferred Data Type}' in the IGC UI.
     * <br><br>
     * Can be one of the following values:
     * <ul>
     *     <li>INT8 (displayed in the UI as 'INT8')</li>
     *     <li>INT16 (displayed in the UI as 'INT16')</li>
     *     <li>INT32 (displayed in the UI as 'INT32')</li>
     *     <li>INT64 (displayed in the UI as 'INT64')</li>
     *     <li>SFLOAT (displayed in the UI as 'SFLOAT')</li>
     *     <li>DFLOAT (displayed in the UI as 'DFLOAT')</li>
     *     <li>QFLOAT (displayed in the UI as 'QFLOAT')</li>
     *     <li>DECIMAL (displayed in the UI as 'DECIMAL')</li>
     *     <li>STRING (displayed in the UI as 'STRING')</li>
     *     <li>BINARY (displayed in the UI as 'BINARY')</li>
     *     <li>BOOLEAN (displayed in the UI as 'BOOLEAN')</li>
     *     <li>DATE (displayed in the UI as 'DATE')</li>
     *     <li>TIME (displayed in the UI as 'TIME')</li>
     *     <li>DATETIME (displayed in the UI as 'DATETIME')</li>
     *     <li>DURATION (displayed in the UI as 'DURATION')</li>
     *     <li>CHOICE (displayed in the UI as 'CHOICE')</li>
     *     <li>ORDERED_GROUP (displayed in the UI as 'ORDERED_GROUP')</li>
     *     <li>UNORDERED_GROUP (displayed in the UI as 'UNORDERED_GROUP')</li>
     *     <li>GUID (displayed in the UI as 'GUID')</li>
     *     <li>UNKNOWN (displayed in the UI as 'UNKNOWN')</li>
     *     <li>JSON (displayed in the UI as 'JSON')</li>
     *     <li>XML (displayed in the UI as 'XML')</li>
     * </ul>
     */
    protected ArrayList<String> inferredDataType;

    /**
     * The {@code inferredLength} property, displayed as '{@literal Inferred Length}' in the IGC UI.
     */
    protected ArrayList<Number> inferredLength;

    /**
     * The {@code inferredFormat} property, displayed as '{@literal Inferred Format}' in the IGC UI.
     */
    protected ArrayList<String> inferredFormat;

    /**
     * The {@code inferredScale} property, displayed as '{@literal Inferred Scale}' in the IGC UI.
     */
    protected ArrayList<Number> inferredScale;

    /**
     * The {@code inferredPrecision} property, displayed as '{@literal Inferred Precision}' in the IGC UI.
     */
    protected ArrayList<Number> inferredPrecision;

    /**
     * The {@code averageValue} property, displayed as '{@literal Average Value}' in the IGC UI.
     */
    protected ArrayList<String> averageValue;

    /**
     * The {@code isInferredForeignKey} property, displayed as '{@literal Inferred Foreign Key}' in the IGC UI.
     */
    protected Boolean isInferredForeignKey;

    /**
     * The {@code isInferredPrimaryKey} property, displayed as '{@literal Inferred Primary Key}' in the IGC UI.
     */
    protected Boolean isInferredPrimaryKey;

    /**
     * The {@code has_DataClassifications} property, displayed as '{@literal Detected data Classifications}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link Classification} objects.
     */
    protected ReferenceList has_DataClassifications;

    /**
     * The {@code nbRecordsTested} property, displayed as '{@literal Number of Records Tested}' in the IGC UI.
     */
    protected ArrayList<Number> nbRecordsTested;

    /**
     * The {@code column_definitions} property, displayed as '{@literal Column Definitions}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link ColumnDefinition} objects.
     */
    protected ReferenceList column_definitions;

    /**
     * The {@code mapped_to_physical_object_attributes} property, displayed as '{@literal Mapped to Physical Object Attributes}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link PhysicalObjectAttribute} objects.
     */
    protected ReferenceList mapped_to_physical_object_attributes;

    /**
     * The {@code read_by_(static)} property, displayed as '{@literal Read by (Static)}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link InformationAsset} objects.
     */
    @JsonProperty("read_by_(static)") protected ReferenceList read_by__static_;

    /**
     * The {@code written_by_(static)} property, displayed as '{@literal Written by (Static)}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link InformationAsset} objects.
     */
    @JsonProperty("written_by_(static)") protected ReferenceList written_by__static_;

    /**
     * The {@code read_by_(design)} property, displayed as '{@literal Read by (Design)}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link InformationAsset} objects.
     */
    @JsonProperty("read_by_(design)") protected ReferenceList read_by__design_;

    /**
     * The {@code written_by_(design)} property, displayed as '{@literal Written by (Design)}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link InformationAsset} objects.
     */
    @JsonProperty("written_by_(design)") protected ReferenceList written_by__design_;

    /**
     * The {@code read_by_(operational)} property, displayed as '{@literal Read by (Operational)}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link InformationAsset} objects.
     */
    @JsonProperty("read_by_(operational)") protected ReferenceList read_by__operational_;

    /**
     * The {@code written_by_(operational)} property, displayed as '{@literal Written by (Operational)}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link InformationAsset} objects.
     */
    @JsonProperty("written_by_(operational)") protected ReferenceList written_by__operational_;

    /**
     * The {@code read_by_(user_defined)} property, displayed as '{@literal Read by (User-Defined)}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link InformationAsset} objects.
     */
    @JsonProperty("read_by_(user_defined)") protected ReferenceList read_by__user_defined_;

    /**
     * The {@code written_by_(user_defined)} property, displayed as '{@literal Written by (User-Defined)}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link InformationAsset} objects.
     */
    @JsonProperty("written_by_(user_defined)") protected ReferenceList written_by__user_defined_;

    /**
     * The {@code impacted_by} property, displayed as '{@literal Impacted by}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link InformationAsset} objects.
     */
    protected ReferenceList impacted_by;

    /**
     * The {@code impacts_on} property, displayed as '{@literal Impacts on}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link InformationAsset} objects.
     */
    protected ReferenceList impacts_on;

    /**
     * The {@code bi_report_query_items} property, displayed as '{@literal BI Report Query Items}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link Reportobject} objects.
     */
    protected ReferenceList bi_report_query_items;

    /**
     * The {@code bi_collection_members} property, displayed as '{@literal BI Collection Members}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link BiCollectionMember} objects.
     */
    protected ReferenceList bi_collection_members;

    /**
     * The {@code suggested_term_assignments} property, displayed as '{@literal Suggested Term Assignments}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link TermAssignment} objects.
     */
    protected ReferenceList suggested_term_assignments;

    /**
     * The {@code data_rule_definitions} property, displayed as '{@literal Data Rule Definitions}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link NonPublishedDataRuleDefinition} objects.
     */
    protected ReferenceList data_rule_definitions;

    /**
     * The {@code data_rule_set_definitions} property, displayed as '{@literal Data Rule Set Definitions}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link NonPublishedDataRuleSet} objects.
     */
    protected ReferenceList data_rule_set_definitions;

    /**
     * The {@code data_rules} property, displayed as '{@literal Data Rules}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DataRule} objects.
     */
    protected ReferenceList data_rules;

    /**
     * The {@code data_rule_sets} property, displayed as '{@literal Data Rule Sets}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DataRuleSet} objects.
     */
    protected ReferenceList data_rule_sets;

    /**
     * The {@code data_policies} property, displayed as '{@literal Data Policies}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link DataPolicy} objects.
     */
    protected ReferenceList data_policies;

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

    /** @see #database_table_or_view */ @JsonProperty("database_table_or_view")  public Reference getDatabaseTableOrView() { return this.database_table_or_view; }
    /** @see #database_table_or_view */ @JsonProperty("database_table_or_view")  public void setDatabaseTableOrView(Reference database_table_or_view) { this.database_table_or_view = database_table_or_view; }

    /** @see #qualityScore */ @JsonProperty("qualityScore")  public String getQualityscore() { return this.qualityScore; }
    /** @see #qualityScore */ @JsonProperty("qualityScore")  public void setQualityscore(String qualityScore) { this.qualityScore = qualityScore; }

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

    /** @see #database_table */ @JsonProperty("database_table")  public Reference getDatabaseTable() { return this.database_table; }
    /** @see #database_table */ @JsonProperty("database_table")  public void setDatabaseTable(Reference database_table) { this.database_table = database_table; }

    /** @see #view */ @JsonProperty("view")  public ReferenceList getView() { return this.view; }
    /** @see #view */ @JsonProperty("view")  public void setView(ReferenceList view) { this.view = view; }

    /** @see #database_alias */ @JsonProperty("database_alias")  public Reference getDatabaseAlias() { return this.database_alias; }
    /** @see #database_alias */ @JsonProperty("database_alias")  public void setDatabaseAlias(Reference database_alias) { this.database_alias = database_alias; }

    /** @see #implements_entity_attributes */ @JsonProperty("implements_entity_attributes")  public ReferenceList getImplementsEntityAttributes() { return this.implements_entity_attributes; }
    /** @see #implements_entity_attributes */ @JsonProperty("implements_entity_attributes")  public void setImplementsEntityAttributes(ReferenceList implements_entity_attributes) { this.implements_entity_attributes = implements_entity_attributes; }

    /** @see #implements_design_columns */ @JsonProperty("implements_design_columns")  public ReferenceList getImplementsDesignColumns() { return this.implements_design_columns; }
    /** @see #implements_design_columns */ @JsonProperty("implements_design_columns")  public void setImplementsDesignColumns(ReferenceList implements_design_columns) { this.implements_design_columns = implements_design_columns; }

    /** @see #type */ @JsonProperty("type")  public String getTheType() { return this.type; }
    /** @see #type */ @JsonProperty("type")  public void setTheType(String type) { this.type = type; }

    /** @see #odbc_type */ @JsonProperty("odbc_type")  public String getOdbcType() { return this.odbc_type; }
    /** @see #odbc_type */ @JsonProperty("odbc_type")  public void setOdbcType(String odbc_type) { this.odbc_type = odbc_type; }

    /** @see #data_type */ @JsonProperty("data_type")  public String getDataType() { return this.data_type; }
    /** @see #data_type */ @JsonProperty("data_type")  public void setDataType(String data_type) { this.data_type = data_type; }

    /** @see #default_value */ @JsonProperty("default_value")  public String getDefaultValue() { return this.default_value; }
    /** @see #default_value */ @JsonProperty("default_value")  public void setDefaultValue(String default_value) { this.default_value = default_value; }

    /** @see #database_domains */ @JsonProperty("database_domains")  public Reference getDatabaseDomains() { return this.database_domains; }
    /** @see #database_domains */ @JsonProperty("database_domains")  public void setDatabaseDomains(Reference database_domains) { this.database_domains = database_domains; }

    /** @see #referenced_by_database_columns */ @JsonProperty("referenced_by_database_columns")  public ReferenceList getReferencedByDatabaseColumns() { return this.referenced_by_database_columns; }
    /** @see #referenced_by_database_columns */ @JsonProperty("referenced_by_database_columns")  public void setReferencedByDatabaseColumns(ReferenceList referenced_by_database_columns) { this.referenced_by_database_columns = referenced_by_database_columns; }

    /** @see #selected_classification */ @JsonProperty("selected_classification")  public Reference getSelectedClassification() { return this.selected_classification; }
    /** @see #selected_classification */ @JsonProperty("selected_classification")  public void setSelectedClassification(Reference selected_classification) { this.selected_classification = selected_classification; }

    /** @see #detected_classifications */ @JsonProperty("detected_classifications")  public ReferenceList getDetectedClassifications() { return this.detected_classifications; }
    /** @see #detected_classifications */ @JsonProperty("detected_classifications")  public void setDetectedClassifications(ReferenceList detected_classifications) { this.detected_classifications = detected_classifications; }

    /** @see #length */ @JsonProperty("length")  public String getLength() { return this.length; }
    /** @see #length */ @JsonProperty("length")  public void setLength(String length) { this.length = length; }

    /** @see #minimum_length */ @JsonProperty("minimum_length")  public Number getMinimumLength() { return this.minimum_length; }
    /** @see #minimum_length */ @JsonProperty("minimum_length")  public void setMinimumLength(Number minimum_length) { this.minimum_length = minimum_length; }

    /** @see #fraction */ @JsonProperty("fraction")  public Number getFraction() { return this.fraction; }
    /** @see #fraction */ @JsonProperty("fraction")  public void setFraction(Number fraction) { this.fraction = fraction; }

    /** @see #position */ @JsonProperty("position")  public Number getPosition() { return this.position; }
    /** @see #position */ @JsonProperty("position")  public void setPosition(Number position) { this.position = position; }

    /** @see #level */ @JsonProperty("level")  public Number getLevel() { return this.level; }
    /** @see #level */ @JsonProperty("level")  public void setLevel(Number level) { this.level = level; }

    /** @see #occurs */ @JsonProperty("occurs")  public ArrayList<String> getOccurs() { return this.occurs; }
    /** @see #occurs */ @JsonProperty("occurs")  public void setOccurs(ArrayList<String> occurs) { this.occurs = occurs; }

    /** @see #start_end_columns */ @JsonProperty("start_end_columns")  public String getStartEndColumns() { return this.start_end_columns; }
    /** @see #start_end_columns */ @JsonProperty("start_end_columns")  public void setStartEndColumns(String start_end_columns) { this.start_end_columns = start_end_columns; }

    /** @see #allows_null_values */ @JsonProperty("allows_null_values")  public Boolean getAllowsNullValues() { return this.allows_null_values; }
    /** @see #allows_null_values */ @JsonProperty("allows_null_values")  public void setAllowsNullValues(Boolean allows_null_values) { this.allows_null_values = allows_null_values; }

    /** @see #unique */ @JsonProperty("unique")  public Boolean getUnique() { return this.unique; }
    /** @see #unique */ @JsonProperty("unique")  public void setUnique(Boolean unique) { this.unique = unique; }

    /** @see #same_as_data_sources */ @JsonProperty("same_as_data_sources")  public ReferenceList getSameAsDataSources() { return this.same_as_data_sources; }
    /** @see #same_as_data_sources */ @JsonProperty("same_as_data_sources")  public void setSameAsDataSources(ReferenceList same_as_data_sources) { this.same_as_data_sources = same_as_data_sources; }

    /** @see #references_database_columns */ @JsonProperty("references_database_columns")  public ReferenceList getReferencesDatabaseColumns() { return this.references_database_columns; }
    /** @see #references_database_columns */ @JsonProperty("references_database_columns")  public void setReferencesDatabaseColumns(ReferenceList references_database_columns) { this.references_database_columns = references_database_columns; }

    /** @see #defined_primary_key */ @JsonProperty("defined_primary_key")  public ReferenceList getDefinedPrimaryKey() { return this.defined_primary_key; }
    /** @see #defined_primary_key */ @JsonProperty("defined_primary_key")  public void setDefinedPrimaryKey(ReferenceList defined_primary_key) { this.defined_primary_key = defined_primary_key; }

    /** @see #defined_non_primary_key */ @JsonProperty("defined_non_primary_key")  public ReferenceList getDefinedNonPrimaryKey() { return this.defined_non_primary_key; }
    /** @see #defined_non_primary_key */ @JsonProperty("defined_non_primary_key")  public void setDefinedNonPrimaryKey(ReferenceList defined_non_primary_key) { this.defined_non_primary_key = defined_non_primary_key; }

    /** @see #implemented_key */ @JsonProperty("implemented_key")  public ReferenceList getImplementedKey() { return this.implemented_key; }
    /** @see #implemented_key */ @JsonProperty("implemented_key")  public void setImplementedKey(ReferenceList implemented_key) { this.implemented_key = implemented_key; }

    /** @see #selected_primary_key */ @JsonProperty("selected_primary_key")  public Boolean getSelectedPrimaryKey() { return this.selected_primary_key; }
    /** @see #selected_primary_key */ @JsonProperty("selected_primary_key")  public void setSelectedPrimaryKey(Boolean selected_primary_key) { this.selected_primary_key = selected_primary_key; }

    /** @see #selected_natural_key */ @JsonProperty("selected_natural_key")  public Boolean getSelectedNaturalKey() { return this.selected_natural_key; }
    /** @see #selected_natural_key */ @JsonProperty("selected_natural_key")  public void setSelectedNaturalKey(Boolean selected_natural_key) { this.selected_natural_key = selected_natural_key; }

    /** @see #defined_foreign_key */ @JsonProperty("defined_foreign_key")  public Boolean getDefinedForeignKey() { return this.defined_foreign_key; }
    /** @see #defined_foreign_key */ @JsonProperty("defined_foreign_key")  public void setDefinedForeignKey(Boolean defined_foreign_key) { this.defined_foreign_key = defined_foreign_key; }

    /** @see #defined_foreign_key_references */ @JsonProperty("defined_foreign_key_references")  public ReferenceList getDefinedForeignKeyReferences() { return this.defined_foreign_key_references; }
    /** @see #defined_foreign_key_references */ @JsonProperty("defined_foreign_key_references")  public void setDefinedForeignKeyReferences(ReferenceList defined_foreign_key_references) { this.defined_foreign_key_references = defined_foreign_key_references; }

    /** @see #defined_foreign_key_referenced */ @JsonProperty("defined_foreign_key_referenced")  public ReferenceList getDefinedForeignKeyReferenced() { return this.defined_foreign_key_referenced; }
    /** @see #defined_foreign_key_referenced */ @JsonProperty("defined_foreign_key_referenced")  public void setDefinedForeignKeyReferenced(ReferenceList defined_foreign_key_referenced) { this.defined_foreign_key_referenced = defined_foreign_key_referenced; }

    /** @see #selected_foreign_key */ @JsonProperty("selected_foreign_key")  public Boolean getSelectedForeignKey() { return this.selected_foreign_key; }
    /** @see #selected_foreign_key */ @JsonProperty("selected_foreign_key")  public void setSelectedForeignKey(Boolean selected_foreign_key) { this.selected_foreign_key = selected_foreign_key; }

    /** @see #selected_foreign_key_references */ @JsonProperty("selected_foreign_key_references")  public ReferenceList getSelectedForeignKeyReferences() { return this.selected_foreign_key_references; }
    /** @see #selected_foreign_key_references */ @JsonProperty("selected_foreign_key_references")  public void setSelectedForeignKeyReferences(ReferenceList selected_foreign_key_references) { this.selected_foreign_key_references = selected_foreign_key_references; }

    /** @see #selected_foreign_key_referenced */ @JsonProperty("selected_foreign_key_referenced")  public ReferenceList getSelectedForeignKeyReferenced() { return this.selected_foreign_key_referenced; }
    /** @see #selected_foreign_key_referenced */ @JsonProperty("selected_foreign_key_referenced")  public void setSelectedForeignKeyReferenced(ReferenceList selected_foreign_key_referenced) { this.selected_foreign_key_referenced = selected_foreign_key_referenced; }

    /** @see #database_indexes */ @JsonProperty("database_indexes")  public ReferenceList getDatabaseIndexes() { return this.database_indexes; }
    /** @see #database_indexes */ @JsonProperty("database_indexes")  public void setDatabaseIndexes(ReferenceList database_indexes) { this.database_indexes = database_indexes; }

    /** @see #index */ @JsonProperty("index")  public ReferenceList getIndex() { return this.index; }
    /** @see #index */ @JsonProperty("index")  public void setIndex(ReferenceList index) { this.index = index; }

    /** @see #validity_tables */ @JsonProperty("validity_tables")  public ReferenceList getValidityTables() { return this.validity_tables; }
    /** @see #validity_tables */ @JsonProperty("validity_tables")  public void setValidityTables(ReferenceList validity_tables) { this.validity_tables = validity_tables; }

    /** @see #uniqueFlag */ @JsonProperty("uniqueFlag")  public Boolean getUniqueflag() { return this.uniqueFlag; }
    /** @see #uniqueFlag */ @JsonProperty("uniqueFlag")  public void setUniqueflag(Boolean uniqueFlag) { this.uniqueFlag = uniqueFlag; }

    /** @see #nullabilityFlag */ @JsonProperty("nullabilityFlag")  public Boolean getNullabilityflag() { return this.nullabilityFlag; }
    /** @see #nullabilityFlag */ @JsonProperty("nullabilityFlag")  public void setNullabilityflag(Boolean nullabilityFlag) { this.nullabilityFlag = nullabilityFlag; }

    /** @see #constantFlag */ @JsonProperty("constantFlag")  public Boolean getConstantflag() { return this.constantFlag; }
    /** @see #constantFlag */ @JsonProperty("constantFlag")  public void setConstantflag(Boolean constantFlag) { this.constantFlag = constantFlag; }

    /** @see #domainType */ @JsonProperty("domainType")  public ArrayList<String> getDomaintype() { return this.domainType; }
    /** @see #domainType */ @JsonProperty("domainType")  public void setDomaintype(ArrayList<String> domainType) { this.domainType = domainType; }

    /** @see #numberCompleteValues */ @JsonProperty("numberCompleteValues")  public ArrayList<Number> getNumbercompletevalues() { return this.numberCompleteValues; }
    /** @see #numberCompleteValues */ @JsonProperty("numberCompleteValues")  public void setNumbercompletevalues(ArrayList<Number> numberCompleteValues) { this.numberCompleteValues = numberCompleteValues; }

    /** @see #numberValidValues */ @JsonProperty("numberValidValues")  public ArrayList<Number> getNumbervalidvalues() { return this.numberValidValues; }
    /** @see #numberValidValues */ @JsonProperty("numberValidValues")  public void setNumbervalidvalues(ArrayList<Number> numberValidValues) { this.numberValidValues = numberValidValues; }

    /** @see #numberEmptyValues */ @JsonProperty("numberEmptyValues")  public ArrayList<Number> getNumberemptyvalues() { return this.numberEmptyValues; }
    /** @see #numberEmptyValues */ @JsonProperty("numberEmptyValues")  public void setNumberemptyvalues(ArrayList<Number> numberEmptyValues) { this.numberEmptyValues = numberEmptyValues; }

    /** @see #numberNullValues */ @JsonProperty("numberNullValues")  public ArrayList<Number> getNumbernullvalues() { return this.numberNullValues; }
    /** @see #numberNullValues */ @JsonProperty("numberNullValues")  public void setNumbernullvalues(ArrayList<Number> numberNullValues) { this.numberNullValues = numberNullValues; }

    /** @see #numberDistinctValues */ @JsonProperty("numberDistinctValues")  public ArrayList<Number> getNumberdistinctvalues() { return this.numberDistinctValues; }
    /** @see #numberDistinctValues */ @JsonProperty("numberDistinctValues")  public void setNumberdistinctvalues(ArrayList<Number> numberDistinctValues) { this.numberDistinctValues = numberDistinctValues; }

    /** @see #numberFormats */ @JsonProperty("numberFormats")  public ArrayList<Number> getNumberformats() { return this.numberFormats; }
    /** @see #numberFormats */ @JsonProperty("numberFormats")  public void setNumberformats(ArrayList<Number> numberFormats) { this.numberFormats = numberFormats; }

    /** @see #numberZeroValues */ @JsonProperty("numberZeroValues")  public ArrayList<Number> getNumberzerovalues() { return this.numberZeroValues; }
    /** @see #numberZeroValues */ @JsonProperty("numberZeroValues")  public void setNumberzerovalues(ArrayList<Number> numberZeroValues) { this.numberZeroValues = numberZeroValues; }

    /** @see #inferredDataType */ @JsonProperty("inferredDataType")  public ArrayList<String> getInferreddatatype() { return this.inferredDataType; }
    /** @see #inferredDataType */ @JsonProperty("inferredDataType")  public void setInferreddatatype(ArrayList<String> inferredDataType) { this.inferredDataType = inferredDataType; }

    /** @see #inferredLength */ @JsonProperty("inferredLength")  public ArrayList<Number> getInferredlength() { return this.inferredLength; }
    /** @see #inferredLength */ @JsonProperty("inferredLength")  public void setInferredlength(ArrayList<Number> inferredLength) { this.inferredLength = inferredLength; }

    /** @see #inferredFormat */ @JsonProperty("inferredFormat")  public ArrayList<String> getInferredformat() { return this.inferredFormat; }
    /** @see #inferredFormat */ @JsonProperty("inferredFormat")  public void setInferredformat(ArrayList<String> inferredFormat) { this.inferredFormat = inferredFormat; }

    /** @see #inferredScale */ @JsonProperty("inferredScale")  public ArrayList<Number> getInferredscale() { return this.inferredScale; }
    /** @see #inferredScale */ @JsonProperty("inferredScale")  public void setInferredscale(ArrayList<Number> inferredScale) { this.inferredScale = inferredScale; }

    /** @see #inferredPrecision */ @JsonProperty("inferredPrecision")  public ArrayList<Number> getInferredprecision() { return this.inferredPrecision; }
    /** @see #inferredPrecision */ @JsonProperty("inferredPrecision")  public void setInferredprecision(ArrayList<Number> inferredPrecision) { this.inferredPrecision = inferredPrecision; }

    /** @see #averageValue */ @JsonProperty("averageValue")  public ArrayList<String> getAveragevalue() { return this.averageValue; }
    /** @see #averageValue */ @JsonProperty("averageValue")  public void setAveragevalue(ArrayList<String> averageValue) { this.averageValue = averageValue; }

    /** @see #isInferredForeignKey */ @JsonProperty("isInferredForeignKey")  public Boolean getIsinferredforeignkey() { return this.isInferredForeignKey; }
    /** @see #isInferredForeignKey */ @JsonProperty("isInferredForeignKey")  public void setIsinferredforeignkey(Boolean isInferredForeignKey) { this.isInferredForeignKey = isInferredForeignKey; }

    /** @see #isInferredPrimaryKey */ @JsonProperty("isInferredPrimaryKey")  public Boolean getIsinferredprimarykey() { return this.isInferredPrimaryKey; }
    /** @see #isInferredPrimaryKey */ @JsonProperty("isInferredPrimaryKey")  public void setIsinferredprimarykey(Boolean isInferredPrimaryKey) { this.isInferredPrimaryKey = isInferredPrimaryKey; }

    /** @see #has_DataClassifications */ @JsonProperty("has_DataClassifications")  public ReferenceList getHasDataclassifications() { return this.has_DataClassifications; }
    /** @see #has_DataClassifications */ @JsonProperty("has_DataClassifications")  public void setHasDataclassifications(ReferenceList has_DataClassifications) { this.has_DataClassifications = has_DataClassifications; }

    /** @see #nbRecordsTested */ @JsonProperty("nbRecordsTested")  public ArrayList<Number> getNbrecordstested() { return this.nbRecordsTested; }
    /** @see #nbRecordsTested */ @JsonProperty("nbRecordsTested")  public void setNbrecordstested(ArrayList<Number> nbRecordsTested) { this.nbRecordsTested = nbRecordsTested; }

    /** @see #column_definitions */ @JsonProperty("column_definitions")  public ReferenceList getColumnDefinitions() { return this.column_definitions; }
    /** @see #column_definitions */ @JsonProperty("column_definitions")  public void setColumnDefinitions(ReferenceList column_definitions) { this.column_definitions = column_definitions; }

    /** @see #mapped_to_physical_object_attributes */ @JsonProperty("mapped_to_physical_object_attributes")  public ReferenceList getMappedToPhysicalObjectAttributes() { return this.mapped_to_physical_object_attributes; }
    /** @see #mapped_to_physical_object_attributes */ @JsonProperty("mapped_to_physical_object_attributes")  public void setMappedToPhysicalObjectAttributes(ReferenceList mapped_to_physical_object_attributes) { this.mapped_to_physical_object_attributes = mapped_to_physical_object_attributes; }

    /** @see #read_by__static_ */ @JsonProperty("read_by_(static)")  public ReferenceList getReadByStatic() { return this.read_by__static_; }
    /** @see #read_by__static_ */ @JsonProperty("read_by_(static)")  public void setReadByStatic(ReferenceList read_by__static_) { this.read_by__static_ = read_by__static_; }

    /** @see #written_by__static_ */ @JsonProperty("written_by_(static)")  public ReferenceList getWrittenByStatic() { return this.written_by__static_; }
    /** @see #written_by__static_ */ @JsonProperty("written_by_(static)")  public void setWrittenByStatic(ReferenceList written_by__static_) { this.written_by__static_ = written_by__static_; }

    /** @see #read_by__design_ */ @JsonProperty("read_by_(design)")  public ReferenceList getReadByDesign() { return this.read_by__design_; }
    /** @see #read_by__design_ */ @JsonProperty("read_by_(design)")  public void setReadByDesign(ReferenceList read_by__design_) { this.read_by__design_ = read_by__design_; }

    /** @see #written_by__design_ */ @JsonProperty("written_by_(design)")  public ReferenceList getWrittenByDesign() { return this.written_by__design_; }
    /** @see #written_by__design_ */ @JsonProperty("written_by_(design)")  public void setWrittenByDesign(ReferenceList written_by__design_) { this.written_by__design_ = written_by__design_; }

    /** @see #read_by__operational_ */ @JsonProperty("read_by_(operational)")  public ReferenceList getReadByOperational() { return this.read_by__operational_; }
    /** @see #read_by__operational_ */ @JsonProperty("read_by_(operational)")  public void setReadByOperational(ReferenceList read_by__operational_) { this.read_by__operational_ = read_by__operational_; }

    /** @see #written_by__operational_ */ @JsonProperty("written_by_(operational)")  public ReferenceList getWrittenByOperational() { return this.written_by__operational_; }
    /** @see #written_by__operational_ */ @JsonProperty("written_by_(operational)")  public void setWrittenByOperational(ReferenceList written_by__operational_) { this.written_by__operational_ = written_by__operational_; }

    /** @see #read_by__user_defined_ */ @JsonProperty("read_by_(user_defined)")  public ReferenceList getReadByUserDefined() { return this.read_by__user_defined_; }
    /** @see #read_by__user_defined_ */ @JsonProperty("read_by_(user_defined)")  public void setReadByUserDefined(ReferenceList read_by__user_defined_) { this.read_by__user_defined_ = read_by__user_defined_; }

    /** @see #written_by__user_defined_ */ @JsonProperty("written_by_(user_defined)")  public ReferenceList getWrittenByUserDefined() { return this.written_by__user_defined_; }
    /** @see #written_by__user_defined_ */ @JsonProperty("written_by_(user_defined)")  public void setWrittenByUserDefined(ReferenceList written_by__user_defined_) { this.written_by__user_defined_ = written_by__user_defined_; }

    /** @see #impacted_by */ @JsonProperty("impacted_by")  public ReferenceList getImpactedBy() { return this.impacted_by; }
    /** @see #impacted_by */ @JsonProperty("impacted_by")  public void setImpactedBy(ReferenceList impacted_by) { this.impacted_by = impacted_by; }

    /** @see #impacts_on */ @JsonProperty("impacts_on")  public ReferenceList getImpactsOn() { return this.impacts_on; }
    /** @see #impacts_on */ @JsonProperty("impacts_on")  public void setImpactsOn(ReferenceList impacts_on) { this.impacts_on = impacts_on; }

    /** @see #bi_report_query_items */ @JsonProperty("bi_report_query_items")  public ReferenceList getBiReportQueryItems() { return this.bi_report_query_items; }
    /** @see #bi_report_query_items */ @JsonProperty("bi_report_query_items")  public void setBiReportQueryItems(ReferenceList bi_report_query_items) { this.bi_report_query_items = bi_report_query_items; }

    /** @see #bi_collection_members */ @JsonProperty("bi_collection_members")  public ReferenceList getBiCollectionMembers() { return this.bi_collection_members; }
    /** @see #bi_collection_members */ @JsonProperty("bi_collection_members")  public void setBiCollectionMembers(ReferenceList bi_collection_members) { this.bi_collection_members = bi_collection_members; }

    /** @see #suggested_term_assignments */ @JsonProperty("suggested_term_assignments")  public ReferenceList getSuggestedTermAssignments() { return this.suggested_term_assignments; }
    /** @see #suggested_term_assignments */ @JsonProperty("suggested_term_assignments")  public void setSuggestedTermAssignments(ReferenceList suggested_term_assignments) { this.suggested_term_assignments = suggested_term_assignments; }

    /** @see #data_rule_definitions */ @JsonProperty("data_rule_definitions")  public ReferenceList getDataRuleDefinitions() { return this.data_rule_definitions; }
    /** @see #data_rule_definitions */ @JsonProperty("data_rule_definitions")  public void setDataRuleDefinitions(ReferenceList data_rule_definitions) { this.data_rule_definitions = data_rule_definitions; }

    /** @see #data_rule_set_definitions */ @JsonProperty("data_rule_set_definitions")  public ReferenceList getDataRuleSetDefinitions() { return this.data_rule_set_definitions; }
    /** @see #data_rule_set_definitions */ @JsonProperty("data_rule_set_definitions")  public void setDataRuleSetDefinitions(ReferenceList data_rule_set_definitions) { this.data_rule_set_definitions = data_rule_set_definitions; }

    /** @see #data_rules */ @JsonProperty("data_rules")  public ReferenceList getDataRules() { return this.data_rules; }
    /** @see #data_rules */ @JsonProperty("data_rules")  public void setDataRules(ReferenceList data_rules) { this.data_rules = data_rules; }

    /** @see #data_rule_sets */ @JsonProperty("data_rule_sets")  public ReferenceList getDataRuleSets() { return this.data_rule_sets; }
    /** @see #data_rule_sets */ @JsonProperty("data_rule_sets")  public void setDataRuleSets(ReferenceList data_rule_sets) { this.data_rule_sets = data_rule_sets; }

    /** @see #data_policies */ @JsonProperty("data_policies")  public ReferenceList getDataPolicies() { return this.data_policies; }
    /** @see #data_policies */ @JsonProperty("data_policies")  public void setDataPolicies(ReferenceList data_policies) { this.data_policies = data_policies; }

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
        "qualityScore",
        "type",
        "odbc_type",
        "data_type",
        "default_value",
        "length",
        "minimum_length",
        "fraction",
        "position",
        "level",
        "occurs",
        "start_end_columns",
        "allows_null_values",
        "unique",
        "selected_primary_key",
        "selected_natural_key",
        "defined_foreign_key",
        "selected_foreign_key",
        "uniqueFlag",
        "nullabilityFlag",
        "constantFlag",
        "domainType",
        "numberCompleteValues",
        "numberValidValues",
        "numberEmptyValues",
        "numberNullValues",
        "numberDistinctValues",
        "numberFormats",
        "numberZeroValues",
        "inferredDataType",
        "inferredLength",
        "inferredFormat",
        "inferredScale",
        "inferredPrecision",
        "averageValue",
        "isInferredForeignKey",
        "isInferredPrimaryKey",
        "nbRecordsTested",
        "created_by",
        "created_on",
        "modified_by",
        "modified_on"
    );
    private static final List<String> STRING_PROPERTIES = Arrays.asList(
        "name",
        "short_description",
        "long_description",
        "qualityScore",
        "type",
        "default_value",
        "length",
        "occurs",
        "start_end_columns",
        "domainType",
        "inferredFormat",
        "averageValue",
        "created_by",
        "modified_by"
    );
    private static final List<String> PAGED_RELATIONAL_PROPERTIES = Arrays.asList(
        "labels",
        "stewards",
        "assigned_to_terms",
        "implements_rules",
        "governed_by_rules",
        "view",
        "implements_entity_attributes",
        "implements_design_columns",
        "referenced_by_database_columns",
        "detected_classifications",
        "same_as_data_sources",
        "references_database_columns",
        "defined_primary_key",
        "defined_non_primary_key",
        "implemented_key",
        "defined_foreign_key_references",
        "defined_foreign_key_referenced",
        "selected_foreign_key_references",
        "selected_foreign_key_referenced",
        "database_indexes",
        "index",
        "validity_tables",
        "has_DataClassifications",
        "column_definitions",
        "mapped_to_physical_object_attributes",
        "read_by_(static)",
        "written_by_(static)",
        "read_by_(design)",
        "written_by_(design)",
        "read_by_(operational)",
        "written_by_(operational)",
        "read_by_(user_defined)",
        "written_by_(user_defined)",
        "impacted_by",
        "impacts_on",
        "bi_report_query_items",
        "bi_collection_members",
        "suggested_term_assignments",
        "data_rule_definitions",
        "data_rule_set_definitions",
        "data_rules",
        "data_rule_sets",
        "data_policies",
        "blueprint_elements",
        "in_collections"
    );
    private static final List<String> ALL_PROPERTIES = Arrays.asList(
        "name",
        "short_description",
        "long_description",
        "database_table_or_view",
        "qualityScore",
        "labels",
        "stewards",
        "assigned_to_terms",
        "implements_rules",
        "governed_by_rules",
        "database_table",
        "view",
        "database_alias",
        "implements_entity_attributes",
        "implements_design_columns",
        "type",
        "odbc_type",
        "data_type",
        "default_value",
        "database_domains",
        "referenced_by_database_columns",
        "selected_classification",
        "detected_classifications",
        "length",
        "minimum_length",
        "fraction",
        "position",
        "level",
        "occurs",
        "start_end_columns",
        "allows_null_values",
        "unique",
        "same_as_data_sources",
        "references_database_columns",
        "defined_primary_key",
        "defined_non_primary_key",
        "implemented_key",
        "selected_primary_key",
        "selected_natural_key",
        "defined_foreign_key",
        "defined_foreign_key_references",
        "defined_foreign_key_referenced",
        "selected_foreign_key",
        "selected_foreign_key_references",
        "selected_foreign_key_referenced",
        "database_indexes",
        "index",
        "validity_tables",
        "uniqueFlag",
        "nullabilityFlag",
        "constantFlag",
        "domainType",
        "numberCompleteValues",
        "numberValidValues",
        "numberEmptyValues",
        "numberNullValues",
        "numberDistinctValues",
        "numberFormats",
        "numberZeroValues",
        "inferredDataType",
        "inferredLength",
        "inferredFormat",
        "inferredScale",
        "inferredPrecision",
        "averageValue",
        "isInferredForeignKey",
        "isInferredPrimaryKey",
        "has_DataClassifications",
        "nbRecordsTested",
        "column_definitions",
        "mapped_to_physical_object_attributes",
        "read_by_(static)",
        "written_by_(static)",
        "read_by_(design)",
        "written_by_(design)",
        "read_by_(operational)",
        "written_by_(operational)",
        "read_by_(user_defined)",
        "written_by_(user_defined)",
        "impacted_by",
        "impacts_on",
        "bi_report_query_items",
        "bi_collection_members",
        "suggested_term_assignments",
        "data_rule_definitions",
        "data_rule_set_definitions",
        "data_rules",
        "data_rule_sets",
        "data_policies",
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
    public static Boolean isDatabaseColumn(Object obj) { return (obj.getClass() == DatabaseColumn.class); }

}
