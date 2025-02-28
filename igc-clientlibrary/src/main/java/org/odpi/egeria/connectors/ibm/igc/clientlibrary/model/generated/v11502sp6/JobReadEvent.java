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
 * POJO for the {@code job_read_event} asset type in IGC, displayed as '{@literal Job Read Event}' in the IGC UI.
 * <br><br>
 * (this code has been generated based on out-of-the-box IGC metadata types;
 *  if modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@Generated("org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.IGCRestModelGenerator")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("job_read_event")
public class JobReadEvent extends Reference {

    public static String getIgcTypeDisplayName() { return "Job Read Event"; }

    /**
     * The {@code reads_from} property, displayed as '{@literal Reads From}' in the IGC UI.
     */
    protected ArrayList<String> reads_from;

    /**
     * The {@code job_run_activity} property, displayed as '{@literal Job Run Activity}' in the IGC UI.
     * <br><br>
     * Will be a single {@link Reference} to a {@link InformationAsset} object.
     */
    protected Reference job_run_activity;

    /**
     * The {@code row_count} property, displayed as '{@literal Rows Processed}' in the IGC UI.
     */
    protected Number row_count;

    /**
     * The {@code time} property, displayed as '{@literal Time}' in the IGC UI.
     */
    protected Date time;

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


    /** @see #reads_from */ @JsonProperty("reads_from")  public ArrayList<String> getReadsFrom() { return this.reads_from; }
    /** @see #reads_from */ @JsonProperty("reads_from")  public void setReadsFrom(ArrayList<String> reads_from) { this.reads_from = reads_from; }

    /** @see #job_run_activity */ @JsonProperty("job_run_activity")  public Reference getJobRunActivity() { return this.job_run_activity; }
    /** @see #job_run_activity */ @JsonProperty("job_run_activity")  public void setJobRunActivity(Reference job_run_activity) { this.job_run_activity = job_run_activity; }

    /** @see #row_count */ @JsonProperty("row_count")  public Number getRowCount() { return this.row_count; }
    /** @see #row_count */ @JsonProperty("row_count")  public void setRowCount(Number row_count) { this.row_count = row_count; }

    /** @see #time */ @JsonProperty("time")  public Date getTime() { return this.time; }
    /** @see #time */ @JsonProperty("time")  public void setTime(Date time) { this.time = time; }

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
        "reads_from",
        "row_count",
        "time",
        "created_by",
        "created_on",
        "modified_by",
        "modified_on"
    );
    private static final List<String> STRING_PROPERTIES = Arrays.asList(
        "reads_from",
        "created_by",
        "modified_by"
    );
    private static final List<String> PAGED_RELATIONAL_PROPERTIES = new ArrayList<>();
    private static final List<String> ALL_PROPERTIES = Arrays.asList(
        "reads_from",
        "job_run_activity",
        "row_count",
        "time",
        "created_by",
        "created_on",
        "modified_by",
        "modified_on"
    );
    public static List<String> getNonRelationshipProperties() { return NON_RELATIONAL_PROPERTIES; }
    public static List<String> getStringProperties() { return STRING_PROPERTIES; }
    public static List<String> getPagedRelationshipProperties() { return PAGED_RELATIONAL_PROPERTIES; }
    public static List<String> getAllProperties() { return ALL_PROPERTIES; }
    public static Boolean isJobReadEvent(Object obj) { return (obj.getClass() == JobReadEvent.class); }

}
