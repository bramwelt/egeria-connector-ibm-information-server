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
 * POJO for the {@code function_call} asset type in IGC, displayed as '{@literal Function Call}' in the IGC UI.
 * <br><br>
 * (this code has been generated based on out-of-the-box IGC metadata types;
 *  if modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@Generated("org.odpi.egeria.connectors.ibm.igc.clientlibrary.model.IGCRestModelGenerator")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("function_call")
public class FunctionCall extends Reference {

    public static String getIgcTypeDisplayName() { return "Function Call"; }

    /**
     * The {@code function_name} property, displayed as '{@literal Function Name}' in the IGC UI.
     */
    protected String function_name;

    /**
     * The {@code for_transforms} property, displayed as '{@literal For Transforms}' in the IGC UI.
     */
    protected Boolean for_transforms;

    /**
     * The {@code stage_trigger} property, displayed as '{@literal Stage Trigger}' in the IGC UI.
     */
    protected Boolean stage_trigger;

    /**
     * The {@code call_context} property, displayed as '{@literal Call Context}' in the IGC UI.
     * <br><br>
     * Can be one of the following values:
     * <ul>
     *     <li>BEFORE (displayed in the UI as 'BEFORE')</li>
     *     <li>AFTER (displayed in the UI as 'AFTER')</li>
     *     <li>DEFAULT (displayed in the UI as 'DEFAULT')</li>
     * </ul>
     */
    protected String call_context;

    /**
     * The {@code executes_function} property, displayed as '{@literal Executes Function}' in the IGC UI.
     * <br><br>
     * Will be a single {@link Reference} to a {@link Function} object.
     */
    protected Reference executes_function;

    /**
     * The {@code used_in_function} property, displayed as '{@literal Used In Function}' in the IGC UI.
     * <br><br>
     * Will be a single {@link Reference} to a {@link Function} object.
     */
    protected Reference used_in_function;

    /**
     * The {@code used_in_filter_constraint} property, displayed as '{@literal Used In Filter Constraint}' in the IGC UI.
     * <br><br>
     * Will be a single {@link Reference} to a {@link Filterconstraint} object.
     */
    protected Reference used_in_filter_constraint;


    /** @see #function_name */ @JsonProperty("function_name")  public String getFunctionName() { return this.function_name; }
    /** @see #function_name */ @JsonProperty("function_name")  public void setFunctionName(String function_name) { this.function_name = function_name; }

    /** @see #for_transforms */ @JsonProperty("for_transforms")  public Boolean getForTransforms() { return this.for_transforms; }
    /** @see #for_transforms */ @JsonProperty("for_transforms")  public void setForTransforms(Boolean for_transforms) { this.for_transforms = for_transforms; }

    /** @see #stage_trigger */ @JsonProperty("stage_trigger")  public Boolean getStageTrigger() { return this.stage_trigger; }
    /** @see #stage_trigger */ @JsonProperty("stage_trigger")  public void setStageTrigger(Boolean stage_trigger) { this.stage_trigger = stage_trigger; }

    /** @see #call_context */ @JsonProperty("call_context")  public String getCallContext() { return this.call_context; }
    /** @see #call_context */ @JsonProperty("call_context")  public void setCallContext(String call_context) { this.call_context = call_context; }

    /** @see #executes_function */ @JsonProperty("executes_function")  public Reference getExecutesFunction() { return this.executes_function; }
    /** @see #executes_function */ @JsonProperty("executes_function")  public void setExecutesFunction(Reference executes_function) { this.executes_function = executes_function; }

    /** @see #used_in_function */ @JsonProperty("used_in_function")  public Reference getUsedInFunction() { return this.used_in_function; }
    /** @see #used_in_function */ @JsonProperty("used_in_function")  public void setUsedInFunction(Reference used_in_function) { this.used_in_function = used_in_function; }

    /** @see #used_in_filter_constraint */ @JsonProperty("used_in_filter_constraint")  public Reference getUsedInFilterConstraint() { return this.used_in_filter_constraint; }
    /** @see #used_in_filter_constraint */ @JsonProperty("used_in_filter_constraint")  public void setUsedInFilterConstraint(Reference used_in_filter_constraint) { this.used_in_filter_constraint = used_in_filter_constraint; }

    public static Boolean canBeCreated() { return false; }
    public static Boolean includesModificationDetails() { return false; }
    private static final List<String> NON_RELATIONAL_PROPERTIES = Arrays.asList(
        "function_name",
        "for_transforms",
        "stage_trigger",
        "call_context"
    );
    private static final List<String> STRING_PROPERTIES = Arrays.asList(
        "function_name"
    );
    private static final List<String> PAGED_RELATIONAL_PROPERTIES = new ArrayList<>();
    private static final List<String> ALL_PROPERTIES = Arrays.asList(
        "function_name",
        "for_transforms",
        "stage_trigger",
        "call_context",
        "executes_function",
        "used_in_function",
        "used_in_filter_constraint"
    );
    public static List<String> getNonRelationshipProperties() { return NON_RELATIONAL_PROPERTIES; }
    public static List<String> getStringProperties() { return STRING_PROPERTIES; }
    public static List<String> getPagedRelationshipProperties() { return PAGED_RELATIONAL_PROPERTIES; }
    public static List<String> getAllProperties() { return ALL_PROPERTIES; }
    public static Boolean isFunctionCall(Object obj) { return (obj.getClass() == FunctionCall.class); }

}
