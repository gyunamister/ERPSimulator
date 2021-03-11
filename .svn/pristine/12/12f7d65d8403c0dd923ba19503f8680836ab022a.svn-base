package org.processmining.erpsimulator.plugins;

import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginLevel;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.erpsimulator.models.SapAutomation;

@Plugin(name = "ERP Simulator", level = PluginLevel.PeerReviewed, returnLabels = {"ERP Simulator"}, returnTypes = {SapAutomation.class
		 }, parameterLabels = {})
public class SapAutomationPlugin {
	@UITopiaVariant(affiliation = "PADS", author = "Gyunam Park", email = "gnpark@pads.rwth-aachen.de")
	@PluginVariant(requiredParameterLabels = {})
	public static SapAutomation apply(UIPluginContext context){
		return apply((PluginContext) context);
	}

	public static SapAutomation apply(PluginContext context){
		return new SapAutomation();
	}
	
	
	
	/**
	 * The plug-in variant that runs in any context and uses the default parameters.
	 *
	 * @param context The context to run in.
	 * @param input1 The first input.
	 * @param input2 The second input.
	 * @return The output.
	 */
	@UITopiaVariant(affiliation = "PADS", author = "Gyunam Park", email = "gnpark@pads.rwth-aachen.de")
	@PluginVariant(requiredParameterLabels = { })
	public void runDefault(PluginContext context) {
		// Get the default parameters.
		// Apply the algorithm depending on whether a connection already exists.
	}
}
