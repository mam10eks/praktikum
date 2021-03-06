package com.rapidminer.topicmodel;

import static com.rapidminer.topicmodel.util.ExampleSetHelper.getInstanceName;
import static com.rapidminer.topicmodel.util.ExampleSetHelper.getInstanceSource;
import static com.rapidminer.topicmodel.util.ExampleSetHelper.getInstanceTarget;
import static com.rapidminer.topicmodel.util.MalletInputTransformationHelper.getAlphabetFromExampleSet;
import static com.rapidminer.topicmodel.util.MalletInputTransformationHelper.termFrequenceExampleToFeatures;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureSequence;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.table.AttributeFactory;
import com.rapidminer.example.table.ExampleTable;
import com.rapidminer.example.table.MemoryExampleTable;
import com.rapidminer.tools.Ontology;
import com.rapidminer.topicmodel.util.ExampleSetFormatHelper;
import com.rapidminer.example.table.DataRow;
import com.rapidminer.example.table.DataRowFactory;

public class Helper 
{
	public Helper()
	{
	}
	
	@Test
	public void testCreateExampleSet() throws IOException
	{
		List<Attribute> attributes = createAttributeList();
		
		ExampleTable table = createExampleTable(attributes, createExampleTableRows());
		ExampleSet es = table.createExampleSet();
		
		File file = new File("/home/maik/Schreibtisch/wordVector");
		FileWriter fileWriter = new FileWriter(file);
		ExampleSetFormatHelper.writeExampleSetFormatted(es, fileWriter);
	}
	
	public static ExampleTable createExampleTable(List<Attribute> _attributes, List<List<Number>> _inputRows)
	{
		List<List<Object>> objectInputRows = new ArrayList<List<Object>>();
		
		for(List<Number> numberRow : _inputRows)
		{
			objectInputRows.add(new ArrayList<Object>(numberRow));
		}
		
		return createObjectExampleTable(_attributes, objectInputRows);
	}
	
	
	public static ExampleTable createObjectExampleTable(List<Attribute> _attributes, List<List<Object>> _inputRows)
	{
		MemoryExampleTable table = new MemoryExampleTable(_attributes);
		ObjectDataRowFactory factory = ObjectDataRowFactory.getNewInstance(_attributes);
			
		for(List<Object> row : _inputRows)
		{
			DataRow dataRow = factory.createRow(row);
			table.addDataRow(dataRow);
		}
	
		return table;
	}
	
	
	public static InstanceList getInstanceList(List<ExampleSet> _exampleSetList)
	{
		Alphabet globalAlphabet = getAlphabetFromExampleSet(_exampleSetList);
		
		InstanceList ret = new InstanceList(globalAlphabet, null);
		
		for(ExampleSet exampleSet : _exampleSetList)
		{
			for(Example example : exampleSet)
			{
				int[] features = termFrequenceExampleToFeatures(example, ret.getAlphabet());
				FeatureSequence data = new FeatureSequence(ret.getAlphabet(), features);
				
				ret.add(new Instance(data, getInstanceTarget(example), getInstanceName(example), getInstanceSource(example)));
			}
		}
		
		return ret;
	}
	
	
	
	public static List<Attribute> createAttributeList()
	{
		List<Attribute> ret = new ArrayList<Attribute>();

		ret.add(AttributeFactory.createAttribute("dies", Ontology.NUMERICAL));		//[0]
		ret.add(AttributeFactory.createAttribute("ist",Ontology.NUMERICAL));
		ret.add(AttributeFactory.createAttribute("ein",Ontology.NUMERICAL));
		ret.add(AttributeFactory.createAttribute("hello",Ontology.NUMERICAL));
		ret.add(AttributeFactory.createAttribute("world",Ontology.NUMERICAL));
		ret.add(AttributeFactory.createAttribute("exampleset",Ontology.NUMERICAL));	//[5]
		ret.add(AttributeFactory.createAttribute("zum",Ontology.NUMERICAL));
		ret.add(AttributeFactory.createAttribute("testen",Ontology.NUMERICAL));
		ret.add(AttributeFactory.createAttribute("reicht",Ontology.NUMERICAL));
		ret.add(AttributeFactory.createAttribute("es",Ontology.NUMERICAL));
		ret.add(AttributeFactory.createAttribute("aus",Ontology.NUMERICAL));		//[10]
		ret.add(AttributeFactory.createAttribute("oder",Ontology.NUMERICAL));
		ret.add(AttributeFactory.createAttribute("doch",Ontology.NUMERICAL));
		ret.add(AttributeFactory.createAttribute("nicht",Ontology.NUMERICAL));		//[13]
		
		return ret;
	}
	
	
	public static void close(AutoCloseable _c)
	{
		if(_c != null)
		{
			try 
			{
				_c.close();
			} 
			catch (Exception e) {}
		}
	}
	
	public static void close(Closeable _c)
	{
		if(_c != null)
		{
			try 
			{
				_c.close();
			} 
			catch (Exception e) {}
		}
	}
	
	
	/**
	 * 
	 * @return 
	 */
	public static List<List<Number>> createExampleTableRows()
	{
		List<List<Number>> ret = new ArrayList<List<Number>>();
		
		/*
		 * example one represents the following document:<br>
		 * "Dies ist ein hello world ExampleSet."
		 */
		List<Number> row = new ArrayList<Number>();
		row.add(1);		//[0]
		row.add(1);
		row.add(1);
		row.add(1);
		row.add(1);
		row.add(1);		//[5]
		row.add(0);
		row.add(0);
		row.add(0);
		row.add(0);
		row.add(0);		//[10]
		row.add(0);
		row.add(0);
		row.add(0);		//[13]
		
		ret.add(row);
		
		/*
		 * example two represents the following document:<br>
		 * "Zum testen reicht es aus."
		 */
		row = new ArrayList<Number>();
		row.add(0);		//[0]
		row.add(0);
		row.add(0);
		row.add(0);
		row.add(0);
		row.add(0);		//[5]
		row.add(1);
		row.add(1);
		row.add(1);
		row.add(1);
		row.add(1);		//[10]
		row.add(0);
		row.add(0);
		row.add(0);		//[13]
		
		ret.add(row);
		
		
		/*
		 * example two represents the following document:<br>
		 * "Oder doch nicht?"
		 */
		row = new ArrayList<Number>();
		row.add(0);		//[0]
		row.add(0);
		row.add(0);
		row.add(0);
		row.add(0);
		row.add(0);		//[5]
		row.add(0);
		row.add(0);
		row.add(0);
		row.add(0);
		row.add(0);		//[10]
		row.add(1);
		row.add(1);
		row.add(1);		//[13]
		
		ret.add(row);
		
		
		/*
		 * example two represents the following document:<br>
		 * "Oder oder oder doch nicht nicht nicht?"
		 */
		row = new ArrayList<Number>();
		row.add(0);		//[0]
		row.add(0);
		row.add(0);
		row.add(0);
		row.add(0);
		row.add(0);		//[5]
		row.add(0);
		row.add(0);
		row.add(0);
		row.add(0);
		row.add(0);		//[10]
		row.add(3);
		row.add(1);
		row.add(3);		//[13]
		
		ret.add(row);
		
		
		/*
		 * example two represents the following document:<br>
		 * "Oder doch nicht?"
		 */
		row = new ArrayList<Number>();
		row.add(0);		//[0]
		row.add(3);
		row.add(0);
		row.add(0);
		row.add(0);
		row.add(1);		//[5]
		row.add(0);
		row.add(0);
		row.add(2);
		row.add(0);
		row.add(2);		//[10]
		row.add(0);
		row.add(0);
		row.add(0);		//[13]
		
		ret.add(row);
		
		return ret;
	}
}


abstract class ObjectDataRowFactory
{
	public abstract DataRow createRow(List<Object> _inputRow);
	
	public static ObjectDataRowFactory getNewInstance(List<Attribute> _attributes)
	{
		DataRowFactory factory = new DataRowFactory(DataRowFactory.TYPE_DOUBLE_ARRAY, '.');
		
		return new ObjectDataRowFactoryImpl(_attributes, factory);
	}
	
		
	private ObjectDataRowFactory(){}

	private static final class ObjectDataRowFactoryImpl extends ObjectDataRowFactory
	{
		 private final DataRowFactory factory;
	     private final Attribute[] attributes;
	     
	     private ObjectDataRowFactoryImpl(List<Attribute> _attributes, DataRowFactory _factory)
	     {
	    	 assert _factory != null;
	         assert _attributes != null;
	         this.attributes = _attributes.toArray(new Attribute[_attributes.size()]);
	         this.factory = _factory;
	     }

		@Override
		public DataRow createRow(List<Object> _inputRow) 
		{
			Object[] numbers = _inputRow.toArray(new Object[_inputRow.size()]);
		    return factory.create(numbers, attributes);
		}
	}
}