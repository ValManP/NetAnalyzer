package ui.tools;

import core.ga.controllers.GANetworkController;
import core.model.controllers.NetworkController;
import core.model.inventory.NetworkElement;
import core.model.inventory.impl.storage.DoubleStorage;
import core.model.network.AbstractLink;
import core.model.network.NetworkDescription;
import core.model.network.impl.DoubleNetwork;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.jenetics.Phenotype;
import org.jenetics.engine.EvolutionResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class UIVisualizator {
    private static UIVisualizator instance;

    public static UIVisualizator getInstance() {
        if (instance == null) {
            instance = new UIVisualizator();
        }
        return instance;
    }

    public void paintChart(DoubleNetwork network, DoubleStorage storage, EvolutionResult data, JPanel panel) {
        panel.removeAll();

        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "Population",
                "Capacity",
                "Price",
                createDataset(network, storage, data),
                PlotOrientation.VERTICAL,
                false, false, false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new Dimension(420, 420));
        final XYPlot plot = xylineChart.getXYPlot();

        XYShapeRenderer renderer = new XYShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        plot.setRenderer(renderer);
        panel.add(chartPanel, BorderLayout.PAGE_START);
        panel.repaint();
        panel.revalidate();
    }

    public void paintGraph(DoubleNetwork network, JPanel panel) {

        Graph<String, String> graph = new SparseMultigraph();
        fillGraphFromNetwork(graph, network);

        Layout<String, String> layout = new CircleLayout(graph);
        layout.setSize(new Dimension(350, 500));
        BasicVisualizationServer<String, String> server = new BasicVisualizationServer(layout);
        server.setPreferredSize(new Dimension(350, 500));
        configureGraph(server);

        panel.add(server, BorderLayout.PAGE_START);
        panel.repaint();
        panel.revalidate();
    }

    private void fillGraphFromNetwork(Graph<String, String> graph, DoubleNetwork network) {
        List<NetworkElement> networkElements = network.getNetworkElements();

        networkElements.forEach((element) -> {
            graph.addVertex(element.toString());
        });

        AbstractLink<Double>[][] links = network.getLinksMatrix();

        for (int row = 0; row < network.getSize(); row++) {
            for (int col = 0; col < network.getSize(); col++) {
                if (links[row][col] != null) {
                    graph.addEdge("link" + row + col,
                            networkElements.get(row).toString(),
                            networkElements.get(col).toString(),
                            EdgeType.DIRECTED);
                }
            }
        }
    }

    private XYDataset createDataset(DoubleNetwork network, DoubleStorage storage, EvolutionResult data) {
        final XYSeries series = new XYSeries("Phenotype");

        List<Phenotype> phenotypes = (List<Phenotype>) data.getPopulation().stream().collect(Collectors.toList());
        DoubleNetwork result = null;
        NetworkDescription networkDescription = new NetworkDescription(network, storage);

        for (Phenotype phenotype : phenotypes) {
            result = GANetworkController.applyConfiguration(networkDescription
                    , phenotype.getGenotype().getChromosome());
            series.add(NetworkController.calculateNetworkCapacityByMinValue(result),
                    NetworkController.calculateNetworkCost(result));
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    private void configureGraph(BasicVisualizationServer<String, String> server) {
        server.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        server.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.S);
    }
}
