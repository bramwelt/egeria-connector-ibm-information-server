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
 * POJO for the {@code job_run} asset type in IGC, displayed as '{@literal Job Run}' in the IGC UI.
 * <br><br>
 * (this code has been generated based on out-of-the-box IGC metadata types;
 *  if modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@Generated("org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.IGCRestModelGenerator")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("job_run")
public class JobRun extends Reference {

    public static String getIgcTypeDisplayName() { return "Job Run"; }

    /**
     * The {@code translated_summary} property, displayed as '{@literal Name}' in the IGC UI.
     */
    protected String translated_summary;

    /**
     * The {@code job} property, displayed as '{@literal Job}' in the IGC UI.
     * <br><br>
     * Will be a single {@link Reference} to a {@link Dsjob} object.
     */
    protected Reference job;

    /**
     * The {@code invocation_id} property, displayed as '{@literal Invocation ID}' in the IGC UI.
     */
    protected String invocation_id;

    /**
     * The {@code wave_number} property, displayed as '{@literal Wave Number}' in the IGC UI.
     */
    protected String wave_number;

    /**
     * The {@code omd_file_name} property, displayed as '{@literal OMD File Name}' in the IGC UI.
     */
    protected String omd_file_name;

    /**
     * The {@code starting_date} property, displayed as '{@literal Starting Date}' in the IGC UI.
     */
    protected Date starting_date;

    /**
     * The {@code ending_date} property, displayed as '{@literal Ending Date}' in the IGC UI.
     */
    protected Date ending_date;

    /**
     * The {@code translated_status_code} property, displayed as '{@literal Status}' in the IGC UI.
     */
    protected String translated_status_code;

    /**
     * The {@code parameters} property, displayed as '{@literal Parameters}' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link JobParameter} objects.
     */
    protected ReferenceList parameters;

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


    /** @see #translated_summary */ @JsonProperty("translated_summary")  public String getTranslatedSummary() { return this.translated_summary; }
    /** @see #translated_summary */ @JsonProperty("translated_summary")  public void setTranslatedSummary(String translated_summary) { this.translated_summary = translated_summary; }

    /** @see #job */ @JsonProperty("job")  public Reference getJob() { return this.job; }
    /** @see #job */ @JsonProperty("job")  public void setJob(Reference job) { this.job = job; }

    /** @see #invocation_id */ @JsonProperty("invocation_id")  public String getInvocationId() { return this.invocation_id; }
    /** @see #invocation_id */ @JsonProperty("invocation_id")  public void setInvocationId(String invocation_id) { this.invocation_id = invocation_id; }

    /** @see #wave_number */ @JsonProperty("wave_number")  public String getWaveNumber() { return this.wave_number; }
    /** @see #wave_number */ @JsonProperty("wave_number")  public void setWaveNumber(String wave_number) { this.wave_number = wave_number; }

    /** @see #omd_file_name */ @JsonProperty("omd_file_name")  public String getOmdFileName() { return this.omd_file_name; }
    /** @see #omd_file_name */ @JsonProperty("omd_file_name")  public void setOmdFileName(String omd_file_name) { this.omd_file_name = omd_file_name; }

    /** @see #starting_date */ @JsonProperty("starting_date")  public Date getStartingDate() { return this.starting_date; }
    /** @see #starting_date */ @JsonProperty("starting_date")  public void setStartingDate(Date starting_date) { this.starting_date = starting_date; }

    /** @see #ending_date */ @JsonProperty("ending_date")  public Date getEndingDate() { return this.ending_date; }
    /** @see #ending_date */ @JsonProperty("ending_date")  public void setEndingDate(Date ending_date) { this.ending_date = ending_date; }

    /** @see #translated_status_code */ @JsonProperty("translated_status_code")  public String getTranslatedStatusCode() { return this.translated_status_code; }
    /** @see #translated_status_code */ @JsonProperty("translated_status_code")  public void setTranslatedStatusCode(String translated_status_code) { this.translated_status_code = translated_status_code; }

    /** @see #parameters */ @JsonProperty("parameters")  public ReferenceList getParameters() { return this.parameters; }
    /** @see #parameters */ @JsonProperty("parameters")  public void setParameters(ReferenceList parameters) { this.parameters = parameters; }

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
        "translated_summary",
        "invocation_id",
        "wave_number",
        "omd_file_name",
        "starting_date",
        "ending_date",
        "translated_status_code",
        "created_by",
        "created_on",
        "modified_by",
        "modified_on"
    );
    private static final List<String> STRING_PROPERTIES = Arrays.asList(
        "translated_summary",
        "invocation_id",
        "wave_number",
        "omd_file_name",
        "translated_status_code",
        "created_by",
        "modified_by"
    );
    private static final List<String> PAGED_RELATIONAL_PROPERTIES = Arrays.asList(
        "parameters"
    );
    private static final List<String> ALL_PROPERTIES = Arrays.asList(
        "translated_summary",
        "job",
        "invocation_id",
        "wave_number",
        "omd_file_name",
        "starting_date",
        "ending_date",
        "translated_status_code",
        "parameters",
        "created_by",
        "created_on",
        "modified_by",
        "modified_on"
    );
    public static List<String> getNonRelationshipProperties() { return NON_RELATIONAL_PROPERTIES; }
    public static List<String> getStringProperties() { return STRING_PROPERTIES; }
    public static List<String> getPagedRelationshipProperties() { return PAGED_RELATIONAL_PROPERTIES; }
    public static List<String> getAllProperties() { return ALL_PROPERTIES; }
    public static Boolean isJobRun(Object obj) { return (obj.getClass() == JobRun.class); }

}
