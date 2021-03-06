package org.processmining.erpsimulator.plugins;

import javax.swing.JPanel;

import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.contexts.uitopia.annotations.Visualizer;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginLevel;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.erpsimulator.models.SapAutomation;
import org.processmining.erpsimulator.ui.HomeView;

@Plugin(name = "ERP Simulator", level = PluginLevel.PeerReviewed, returnLabels = {"Control view"}, returnTypes = {JPanel.class}, parameterLabels = {"test"})
@Visualizer
public class ControlPlugin {
	@PluginVariant(requiredParameterLabels = {0})
	public static JPanel visualize (PluginContext context, SapAutomation sapAutomation){
		return new HomeView(context, sapAutomation);
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
