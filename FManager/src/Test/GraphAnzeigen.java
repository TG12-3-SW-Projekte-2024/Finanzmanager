package Test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.mindfusion.charting.FunctionSeries;
import com.mindfusion.charting.GridType;
import com.mindfusion.charting.swing.LineChart;
import com.mindfusion.drawing.DashStyle;
import com.mindfusion.drawing.SolidBrush;

public class GraphAnzeigen extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					new GraphAnzeigen().setVisible(true);			
				}
				catch (Exception exp)
				{
				}
			}
		});

	}

	protected GraphAnzeigen()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 551);
		setTitle("Saldoverlauf");
		
		getContentPane().add(initializeChart(), BorderLayout.CENTER);
	}
	private LineChart initializeChart()
	{
		LineChart lineChart = new LineChart();
		
		FunctionSeries series1;
		
		try {
			series1 = new FunctionSeries("y=m*x+n",
					1000, -5, 5);
			series1.setTitle("Lineare Funktion");
			lineChart.getSeries().add(series1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return lineChart;
	}
}
