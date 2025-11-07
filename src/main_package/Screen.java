package main_package;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Screen extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final int width = 400, height = 400;
	
	public NeuralNetwork nn;
	public Data[] training_data;
	public int cycles = 0;
	
	public Timer timer = new Timer(0, this);
	
	public int resolution = 5;
	
	public Screen() {
		setFocusable(true);
		setPreferredSize(new Dimension(width, height));
		
		timer.start();
		
		init();
	}
	
	public void init() {
		training_data = new Data[]{
			new Data(new double[]{0, 0}, new double[]{0}),
			new Data(new double[]{0.25, 0}, new double[]{1}),
			new Data(new double[]{0.5, 0}, new double[]{0}),
			new Data(new double[]{0.75, 0}, new double[]{1}),
			new Data(new double[]{1, 0}, new double[]{0}),
			
			new Data(new double[]{0, 0.25}, new double[]{1}),
			new Data(new double[]{0.25, 0.25}, new double[]{0}),
			new Data(new double[]{0.5, 0.25}, new double[]{1}),
			new Data(new double[]{0.75, 0.25}, new double[]{0}),
			new Data(new double[]{1, 0.25}, new double[]{1}),
			
			new Data(new double[]{0, 0.5}, new double[]{0}),
			new Data(new double[]{0.25, 0.5}, new double[]{1}),
			new Data(new double[]{0.5, 0.5}, new double[]{0}),
			new Data(new double[]{0.75, 0.5}, new double[]{1}),
			new Data(new double[]{1, 0.5}, new double[]{0}),
			
			new Data(new double[]{0, 0.75}, new double[]{1}),
			new Data(new double[]{0.25, 0.75}, new double[]{0}),
			new Data(new double[]{0.5, 0.75}, new double[]{1}),
			new Data(new double[]{0.75, 0.75}, new double[]{0}),
			new Data(new double[]{1, 0.75}, new double[]{1}),
			
			new Data(new double[]{0, 1}, new double[]{0}),
			new Data(new double[]{0.25, 1}, new double[]{1}),
			new Data(new double[]{0.5, 1}, new double[]{0}),
			new Data(new double[]{0.75, 1}, new double[]{1}),
			new Data(new double[]{1, 1}, new double[]{0}),
			
		};
		
		nn = new NeuralNetwork(2, 20, 1);
		nn.setLr(0.1);
	}
	
	public void training() {
		for (int i = 0; i < 1000; i++) {
			Data data = training_data[new Random().nextInt(training_data.length)];
			nn.train(data.getInputs(), data.getTarget());
		}
		/*
		System.out.println("Input: 1, 0  Output: " + nn.predict(new double[]{1,0})[0]);
		System.out.println("Input: 0, 1  Output: " + nn.predict(new double[]{0,1})[0]);
		System.out.println("Input: 1, 1  Output: " + nn.predict(new double[]{1,1})[0]);
		System.out.println("Input: 0, 0  Output: " + nn.predict(new double[]{0,0})[0] + "\n");
		*/
		cycles++;
		System.out.println("Cycle: " + cycles*1000);
		
	}
	
	public void paint(Graphics g) {
		g.clearRect(0, 0, width, height);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		int cols = width / resolution;
		int rows = height / resolution;
		
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				double x1 = (double)i / (double)cols;
				double x2 = (double)j / (double)rows;
				double[] inputs = new double[]{x1, x2};
				//System.out.println("x1: " + x1 + "  x2: " + x2);
				int y = (int) Math.floor(nn.predict(inputs)[0] * 255);
				g.setColor(new Color(y, y, y));
				g.fillRect(i * resolution, j * resolution, resolution, resolution);
			}
		}
		
		
	}
	
	public class Data {
		public double[] inputs = new double[2];
		public double[] target = new double[1];
		
		public Data(double[] inputs, double[] target) {
			this.inputs = inputs;
			this.target = target;
		}

		public double[] getInputs() {
			return inputs;
		}

		public double[] getTarget() {
			return target;
		}
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			training();
			repaint();
			/*
			double i = (double)10 / (double)100;
			System.out.println(i);
			*/
		}
		
	}
	
}
