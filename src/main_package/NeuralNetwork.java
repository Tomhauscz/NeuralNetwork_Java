package main_package;

import java.util.function.Function;

public class NeuralNetwork {
	
	public int input_nodes, hidden_nodes, output_nodes;
	
	Function<Double, Double> sigmoid = x -> 1 / (1 + Math.exp(-x));
	//Function<Double, Double> dsigmoid = x -> sigmoid.apply(x) * (1 - sigmoid.apply(x));
	Function<Double, Double> dsigmoid = y -> y * (1 - y);
	
	private Matrix weights_ih, weights_ho, bias_h, bias_o;
	
	private Matrix hidden, inputs;
	
	public double lr;
	
	public NeuralNetwork(int input_nodes, int hidden_nodes, int output_nodes) {
		this.input_nodes = input_nodes;
		this.hidden_nodes = hidden_nodes;
		this.output_nodes = output_nodes;
		
		weights_ih = new Matrix(hidden_nodes, input_nodes);
		weights_ho = new Matrix(output_nodes, hidden_nodes);
		weights_ih.randomize();
		//this.weights_ih.display();
		weights_ho.randomize();
		//this.weights_ho.display();
		
		bias_h = new Matrix(hidden_nodes, 1);
		bias_o = new Matrix(output_nodes, 1);
		bias_h.randomize();
		bias_o.randomize();
		//bias_h.display();
		//bias_o.display();
		lr = 0.05;
	}
	

	public Matrix getWeights_ih() {
		return weights_ih;
	}


	public Matrix getWeights_ho() {
		return weights_ho;
	}


	public Matrix getBias_h() {
		return bias_h;
	}


	public Matrix getBias_o() {
		return bias_o;
	}


	public void setLr(double lr) {
		this.lr = lr;
	}


	public double[] predict(double[] input_array) {
		// Generating the Hidden Outputs
		inputs = Matrix.fromArray(input_array);
		hidden = Matrix.multiply(weights_ih, inputs);
		hidden.add(bias_h);
		
		// Activation function
		hidden = Matrix.map(hidden, sigmoid);
		
		// Generating the Output
		Matrix output = Matrix.multiply(weights_ho, hidden);
		output.add(bias_o);
		output = Matrix.map(output, sigmoid);
		
		return output.toArray();
	}
	
	public void train(double[] input_array, double[] targets_array) {
		
		// Generating the Hidden Outputs
		inputs = Matrix.fromArray(input_array);
		hidden = Matrix.multiply(weights_ih, inputs);
		hidden.add(bias_h);
		// Activation function
		hidden = Matrix.map(hidden, sigmoid);
		
		// Generating the Output
		Matrix outputs = Matrix.multiply(weights_ho, hidden);
		outputs.add(bias_o);
		outputs = Matrix.map(outputs, sigmoid);
		
		// END OF FEEDFORWARD
		
		// Convert arrays to matrix objects
		Matrix targets = Matrix.fromArray(targets_array);
		
		// Clculate the error
		// ERROR = TARGETS - OUTPUTS
		Matrix output_errors = Matrix.subtract(targets, outputs);
		
		// Calulate output gradient
		Matrix gradient = Matrix.map(outputs, dsigmoid);
		gradient.multiply(output_errors);
		gradient.multiply(lr);
		
		
		// Calculate output -> hidden deltas
		Matrix hidden_T = Matrix.transpose(hidden);
		
		Matrix weights_ho_deltas = Matrix.multiply(gradient, hidden_T);
		
		// Adjust wieghts by deltas
		weights_ho.add(weights_ho_deltas);
		// Adjust the bias by its deltas (which is just the gradients)
		bias_o.add(gradient);		
		
		// Calculate the hidden layer errors
		Matrix who_t = Matrix.transpose(weights_ho);
		Matrix hidden_errors = Matrix.multiply(who_t, output_errors);
		
		// Calculate hidden gradient
		Matrix hidden_gradient = Matrix.map(hidden, dsigmoid);
		hidden_gradient.multiply(hidden_errors);
		hidden_gradient.multiply(lr);
		
		// Calculate input -> hidden deltas
		Matrix input_T = Matrix.transpose(inputs);
		Matrix weights_ih_deltas = Matrix.multiply(hidden_gradient, input_T);
		
		weights_ih.add(weights_ih_deltas);
		// Adjust the bias by its deltas (which is just gradients)
		bias_h.add(hidden_gradient);
		
		/*
		outputs.display();
		targets.display();
		output_errors.display();
		*/
	}
}
