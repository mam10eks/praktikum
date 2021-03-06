package com.rapidminer.topicmodel.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.table.AttributeFactory;
import com.rapidminer.example.table.DoubleArrayDataRow;
import com.rapidminer.example.table.MemoryExampleTable;
import com.rapidminer.tools.Ontology;
import com.rapidminer.topicmodel.util.ExampleSetFormatHelper;

/**
 * This is a junit test class to determine the correct functionality of the util class
 * {@link com.rapidminer.topicmodel.util.ExampleSetFormatHelper ExampleSetFormatHelper}.<br>
 * <br>
 * There will be some output written to a file some times in a random order.
 * One can only have a look at the last test in this file since the file will be created new each time.
 * One can replace ExampleSetFormatHelper.writeExampleSetFormattedToMyFile(ExampleSet _exampleSet) by 
 * writeExampleSetFormatted(ExampleSet _exampleSet, Writer _writer) specify another output kind.
 * 
 * 
 * @author maik
 * 
 */
public class OperatorTest 
{
	public OperatorTest()
	{
		
	}
	
	
	@Test
	public void testSymmetricExample() throws IOException
	{
		testDoWork(18, 18);
	}
	
	@Test
	public void testAsymmetricOne() throws IOException
	{
		testDoWork(1111, 15);
	}
	
	
	@Test
	public void testAsymmetricTwo() throws IOException
	{
		testDoWork(6, 19);
	}
	
	@Test
	public void testAsymmetricTree() throws IOException
	{
		testDoWork(99,20);
	}
	
	/**
	 * Write a dummy ExampleSet.<br>
	 * Only useful for test purposes.
	 * 
	 * @param _rows number of rows in the ExampleSet.
	 * @param attributCount number of attributes of the ExampleSet.
	 */
	private void testDoWork(int _rows, int attributCount) throws IOException
	{
		// create attribute list
		List <Attribute> attributes = new LinkedList<Attribute>();
		
		for ( int a = 0; a < attributCount; a++) 
		{
			attributes.add( AttributeFactory.createAttribute ("att" + a,Ontology.REAL));
		}
		
		Attribute label = AttributeFactory.createAttribute ("label", Ontology.NOMINAL);
		attributes.add(label);
		
		//create table
		MemoryExampleTable table = new MemoryExampleTable(attributes);
		// fill table (here : only real values )
		for (int d = 0; d < _rows; d++) 
		{
			double[] data = new double[attributes.size()];
			for (int a = 0; a < attributes.size(); a++) 
			{
				//fill with proper data here
				data[a] = 121.4;
			}
				// maps the nominal classification to a double value
				data[data.length - 1] = label.getMapping().mapString("hu");
				//add data row
				table.addDataRow(new DoubleArrayDataRow(data));
		}
		// create example set
		ExampleSet exampleSet = table.createExampleSet(label);
			
		File file = new File("/home/maik/Schreibtisch/wordVector");
		FileWriter fileWriter = new FileWriter(file);
		
		ExampleSetFormatHelper.writeExampleSetFormatted(exampleSet, fileWriter);
	}
}