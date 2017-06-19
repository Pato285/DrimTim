<<<<<<< HEAD
package org.mdp.hadoop.cli;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


/**
 * Java class to add the counts of pairs of 
 * actresses/actors together
 * 
 * @author Aidan
 */
public class CountCoStars {

	/**
	 * This is the Mapper Class. This sends key-value pairs to different machines
	 * based on the key.
	 * 
	 * Remember that the generic is Mapper<InputKey, InputValue, MapKey, MapValue>
	 * 
	 * InputKey we don't care about (a LongWritable will be passed as the input
	 * file offset, but we don't care; we can also set as Object)
	 * 
	 * InputKey will be Text: a line of the file
	 * 
	 * MapKey will be Text: the star pair
	 * 
	 * MapValue will be IntWritable: the star pair count
	 * 
	 * @author Aidan
	 *
	 */
	public static class CountCoStarsMapper extends Mapper<Object, Text, Text, IntWritable>{

		/**
		 * @throws InterruptedException 
		 * 
		 * This is the map method that you're goint to write. :)
		 * 
		 */
		@Override
		public void map(Object key, Text value, Context context)
						throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			context.write(new Text(split[0]),new IntWritable(Integer.parseInt(split[1])));
		}
	}

	/**
	 * This is the Reducer Class.
	 * 
	 * This collects sets of key-value pairs with the same key on one machine. 
	 * 
	 * Remember that the generic is Reducer<MapKey, MapValue, OutputKey, OutputValue>
	 * 
	 * @author Aidan
	 *
	 */
	public static class CountCoStarsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

		/**
		 * @throws InterruptedException 
		 * 
		 * 
		 * MapKey will be Text: the star pair
		 * 
		 * MapValue will be IntWritable: the raw star pair counts
		 * 
		 * OutputKey will be Text: the star pair
		 * 
		 * OutputValue will be IntWritable: the total star pair count
		 */
		@Override
		public void reduce(Text key, Iterable<IntWritable> values, Context context) 
				throws IOException, InterruptedException {
			int sum = 0;
			for(IntWritable value : values) {
				sum += value.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}
	public static class WholePassMapper extends Mapper<Object, Text, Text, IntWritable>{

	    /**
	     * @throws InterruptedException 
	     * 
	     * This is the map method that you're goint to write. :)
	     * 
	     */
	    @Override
	    public void map(Object key, Text value, Context context)
	            throws IOException, InterruptedException {
	      String[] split = value.toString().split("\t");
	      context.write(new Text(split[1]),new IntWritable(1));
	    }
	  }

	  public static class WholePassReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	    @Override
	    public void reduce(Text key, Iterable<IntWritable> values, Context context) 
	        throws IOException, InterruptedException {
	      int sum = 0;
	      for(IntWritable value : values) {
	        sum += value.get();
	      }
	      context.write(key, new IntWritable(sum));
	    }
	  }
	/**
	 * Main method that sets up and runs the job
	 * 
	 * @param args First argument is input, second is output
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage: "+CountCoStars.class.getName()+" <in> <out>");
			System.exit(2);
		}
		String inputLocation = otherArgs[0];
		String outputLocation = otherArgs[1];
		
		Job job = Job.getInstance(new Configuration());
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(WholePassMapper.class);
		job.setReducerClass(WholePassReducer.class);
		
		FileInputFormat.setInputPaths(job, new Path(inputLocation));
		FileOutputFormat.setOutputPath(job, new Path(outputLocation));
		
		job.setJarByClass(CountCoStars.class);
		job.waitForCompletion(true);
	}	
}
=======
package org.mdp.hadoop.cli;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.devewm.pwdstrength.PasswordStrengthMeter;


/**
 * Java class to add the counts of pairs of 
 * actresses/actors together
 * 
 * @author Aidan
 */
public class CountCoStars {
	
	public static class WholePassMapper extends Mapper<Object, Text, Text, IntWritable>{
		@Override
		public void map(Object key, Text value, Context context)
						throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			context.write(new Text(split[1]),new IntWritable(1));
		}
	}

	/**
	 * Este mapea Text a ints.
	 * 
	 * @author Franco
	 *
	 */
	public static class SumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		@Override
		public void reduce(Text key, Iterable<IntWritable> values, Context context) 
				throws IOException, InterruptedException {
			int sum = 0;
			for(IntWritable value : values) {
				sum += value.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}
	
	public static class LengthMapper extends Mapper<Object, Text, IntWritable, IntWritable>{
		@Override
		public void map(Object key, Text value, Context context)
						throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			context.write(new IntWritable(split[0].length()),new IntWritable(1));
		}
	}

	/**
	 * Este mapea ints a ints.
	 * 
	 * @author Franco
	 *
	 */
	public static class SumReducer2 extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
		@Override
		public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) 
				throws IOException, InterruptedException {
			int sum = 0;
			for(IntWritable value : values) {
				sum += value.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}
	
	public static class TypeMapper extends Mapper<Object, Text, Text, IntWritable>{
		@Override
		public void map(Object key, Text value, Context context)
						throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			context.write(new Text(AuxFun.typesOfChars(split[1])),new IntWritable(1));
		}
	}
	
	public static class UniqueMapper extends Mapper<Object, Text, IntWritable, IntWritable>{
		@Override
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			context.write(new IntWritable(AuxFun.uniqueChars(split[1]).length()),new IntWritable(1));
		}
	}
	
	public static class UniqueSetMapper extends Mapper<Object, Text, Text, IntWritable>{
		@Override
		public void map(Object key, Text value, Context context)
						throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			context.write(new Text(AuxFun.uniqueChars(split[1])),new IntWritable(1));
		}
	}
	
	public static class LevenshteinDistanceMapper extends Mapper<Object, Text, IntWritable, IntWritable>{
		@Override
		public void map(Object key, Text value, Context context)
						throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			context.write(new IntWritable(AuxFun.levenshteinDistance(split[0], split[1])),new IntWritable(1));
		}
	}
	
	public static class PasswordStrengthMapper extends Mapper<Object, Text, IntWritable, IntWritable>{
		@Override
		public void map(Object key, Text value, Context context)
						throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			PasswordStrengthMeter pw = PasswordStrengthMeter.getInstance();
		    int tries = pw.iterationCount(split[1]).toString().length();
			context.write(new IntWritable(tries),new IntWritable(1));
		}
	}
	
	public static class CharMapper extends Mapper<Object, Text, Text, IntWritable>{
		@Override
		public void map(Object key, Text value, Context context)
						throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			
			char[] charArray = split[0].toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				context.write(new Text(Character.toString(charArray[i])),new IntWritable(1));
			}
		}
	}
	

	/**
	 * Main method that sets up and runs the job
	 * 
	 * @param args First argument is input, second is output
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage: "+CountCoStars.class.getName()+" <in> <out>");
			System.exit(2);
		}
		String inputLocation = otherArgs[0];
		String outputLocation = otherArgs[1];
		
		String WholePassFileName = "asd"; 
		String LengthFileName = "asd"; 
		String TypeFileName = "asd"; 
		String UniqueFileName = "asd"; 
		String UniqueSetFileName = "asd"; 
		String LevenshteinFileName = "asd"; 
		String StrengthFileName = "asd"; 
		String CharFileName = "asd"; 
		
		// Whole pass
		
		Job job = Job.getInstance(new Configuration());
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(WholePassMapper.class);
		job.setReducerClass(SumReducer.class);
		
		FileInputFormat.setInputPaths(job, new Path(inputLocation));
		FileOutputFormat.setOutputPath(job, new Path(outputLocation + WholePassFileName));
		
		job.setJarByClass(CountCoStars.class);
		job.waitForCompletion(true);
		
		// Length
		
		job = Job.getInstance(new Configuration());
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(LengthMapper.class);
		job.setReducerClass(SumReducer2.class);
		
		FileInputFormat.setInputPaths(job, new Path(inputLocation));
		FileOutputFormat.setOutputPath(job, new Path(outputLocation + LengthFileName));
		
		job.setJarByClass(CountCoStars.class);
		job.waitForCompletion(true);

		
		// Type
		
		job = Job.getInstance(new Configuration());
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(TypeMapper.class);
		job.setReducerClass(SumReducer.class);
		
		FileInputFormat.setInputPaths(job, new Path(inputLocation));
		FileOutputFormat.setOutputPath(job, new Path(outputLocation + TypeFileName));
		
		job.setJarByClass(CountCoStars.class);
		job.waitForCompletion(true);

		
		// Unique
		
		job = Job.getInstance(new Configuration());
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(UniqueMapper.class);
		job.setReducerClass(SumReducer2.class);
		
		FileInputFormat.setInputPaths(job, new Path(inputLocation));
		FileOutputFormat.setOutputPath(job, new Path(outputLocation + UniqueFileName));
		
		job.setJarByClass(CountCoStars.class);
		job.waitForCompletion(true);

		
		// Unique set
		
		job = Job.getInstance(new Configuration());
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(UniqueSetMapper.class);
		job.setReducerClass(SumReducer.class);
		
		FileInputFormat.setInputPaths(job, new Path(inputLocation));
		FileOutputFormat.setOutputPath(job, new Path(outputLocation + UniqueSetFileName));
		
		job.setJarByClass(CountCoStars.class);
		job.waitForCompletion(true);
		
		
		// Levenshtein distance

		job = Job.getInstance(new Configuration());
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(LevenshteinDistanceMapper.class);
		job.setReducerClass(SumReducer2.class);
		
		FileInputFormat.setInputPaths(job, new Path(inputLocation));
		FileOutputFormat.setOutputPath(job, new Path(outputLocation + LevenshteinFileName));
		
		job.setJarByClass(CountCoStars.class);
		job.waitForCompletion(true);
		
		
		// Strength

		job = Job.getInstance(new Configuration());
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(PasswordStrengthMapper.class);
		job.setReducerClass(SumReducer2.class);
		
		FileInputFormat.setInputPaths(job, new Path(inputLocation));
		FileOutputFormat.setOutputPath(job, new Path(outputLocation + StrengthFileName));
		
		job.setJarByClass(CountCoStars.class);
		job.waitForCompletion(true);
		
		
		// Char

		job = Job.getInstance(new Configuration());
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(CharMapper.class);
		job.setReducerClass(SumReducer.class);
		
		FileInputFormat.setInputPaths(job, new Path(inputLocation));
		FileOutputFormat.setOutputPath(job, new Path(outputLocation + CharFileName));
		
		job.setJarByClass(CountCoStars.class);
		job.waitForCompletion(true);

	}	
}
>>>>>>> branch 'master' of https://github.com/Pato285/DrimTim.git
