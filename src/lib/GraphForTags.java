package lib;

import com.google.common.base.Joiner;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NamedMethodGenerator;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_DARKENPeer;

import uk.ac.ox.oii.jsonexporter.JSONExporter;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.gephi.appearance.api.*;
import org.gephi.appearance.plugin.PartitionElementColorTransformer;
import org.gephi.appearance.plugin.palette.Palette;
import org.gephi.appearance.plugin.palette.PaletteManager;
import org.gephi.graph.api.*;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.spi.GraphExporter;
import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.forceAtlas.ForceAtlas;
import org.gephi.layout.plugin.forceAtlas.ForceAtlasLayout;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2Builder;
import org.gephi.layout.plugin.noverlap.NoverlapLayout;
import org.gephi.layout.plugin.scale.Expand;
import org.gephi.layout.spi.Layout;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.statistics.plugin.Degree;
import org.gephi.statistics.plugin.EigenvectorCentrality;
import org.gephi.statistics.plugin.GraphDistance;
import org.gephi.statistics.plugin.Modularity;
import org.gephi.utils.progress.ProgressTicket;
import org.gephi.utils.progress.ProgressTicketProvider;
import org.openide.util.Lookup;

import java.awt.Color;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GraphForTags {

    /* graph */
    ProjectController pc;
    GraphModel graphModel;
    UndirectedGraph undirectedGraph;
    Workspace workspace;
    String theWorkingDirPath;
    Integer means = 0;
    Integer stdevs = 0;
    AppearanceController appearanceController;
    AppearanceModel appearanceModel;

    public GraphForTags() {

        this.pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        this.workspace = pc.getCurrentWorkspace();

        this.graphModel =  Lookup.getDefault().lookup(GraphController.class).getGraphModel(this.workspace);
        this.appearanceController = Lookup.getDefault().lookup(AppearanceController.class);
        this.appearanceModel = appearanceController.getModel();



        this.undirectedGraph = graphModel.getUndirectedGraph();

    }

    
    public Integer getNodes() {
    	Integer i = 0;
    	i = this.undirectedGraph.getNodeCount();
    	return i;
    }
    public void addHandleNode(String screename) {
        Node aNode = graphModel.factory().newNode(screename);
        aNode.setLabel(screename);
        aNode.setSize(10);
        try {
            undirectedGraph.addNode(aNode);
        } catch (Exception e) {
            //System.err.println("Dup node for scree name " + screename);
        }
    }


    public void addEdge(String source, String target) {
        addHandleNode(source);
        addHandleNode(target);

        if (undirectedGraph.getEdge(undirectedGraph.getNode(source), undirectedGraph.getNode(target)) != null) {
            undirectedGraph.getEdge(undirectedGraph.getNode(source), undirectedGraph.getNode(target)).setWeight(undirectedGraph.getEdge(undirectedGraph.getNode(source), undirectedGraph.getNode(target)).getWeight() + 1.0);
        } else if (undirectedGraph.getEdge(undirectedGraph.getNode(target), undirectedGraph.getNode(source)) != null) {
            undirectedGraph.getEdge(undirectedGraph.getNode(target), undirectedGraph.getNode(source)).setWeight(undirectedGraph.getEdge(undirectedGraph.getNode(target), undirectedGraph.getNode(source)).getWeight() + 1.0);
        } else {
            Edge anEdge = graphModel.factory().newEdge(undirectedGraph.getNode(source), undirectedGraph.getNode(target), false);
            anEdge.setWeight(1.0);
            undirectedGraph.addEdge(anEdge);
        }


    }

    
   

    public JsonObject runColorizeEndForceAtals() {
        NonAsyncModularity mod = new NonAsyncModularity();
        mod.setResolution(1.0);
        mod.setRandom(false);
        mod.setUseWeight(true);

        mod.execute(undirectedGraph.getModel());

        /**** colorize graph  ******/

        Column modColumn = this.graphModel.getNodeTable().getColumn(Modularity.MODULARITY_CLASS);
        Function func = appearanceModel.getNodeFunction(undirectedGraph, modColumn, PartitionElementColorTransformer.class);
        Partition partition = ((PartitionFunction) func).getPartition();
        ArrayList<Color> colors = Utilities.generateColorArray(partition.size());

        undirectedGraph.getNodes().forEach(node -> {
           node.setColor(colors.get((Integer) (node.getAttribute("modularity_class"))));
        });

        undirectedGraph.getEdges().forEach(edge -> {
            edge.setColor(edge.getSource().getColor());
        });


        ForceAtlas2 f2 = new ForceAtlas2Builder().buildLayout();
        f2.setGraphModel(graphModel);
        f2.setScalingRatio(700.0);
        f2.setGravity(1.0);
        f2.setStrongGravityMode(true);
        f2.setThreadsCount(7);
        f2.setJitterTolerance(1.0);
        f2.setLinLogMode(false);
        f2.setAdjustSizes(false);

        f2.initAlgo();
        for (int i = 0; i < 2000 && f2.canAlgo(); i++) {
            f2.goAlgo();
        }
        f2.endAlgo();


        ProgressTicketProvider progressProvider = Lookup.getDefault().lookup(ProgressTicketProvider.class);
        ProgressTicket ticket = null;
        if (progressProvider != null) {
            ticket = progressProvider.createTicket("Task name", null);
        }
        
        
        JSONExporter jex = new JSONExporter();
        jex.setWorkspace(this.workspace);
        jex.setExportVisible(false);
        jex.setProgressTicket(ticket);
        
        StringWriter sw = new StringWriter();
        jex.setWriter(sw);
        jex.execute();
        
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        
        return parser.parse(sw.toString()).getAsJsonObject();
        
    }

   
}
