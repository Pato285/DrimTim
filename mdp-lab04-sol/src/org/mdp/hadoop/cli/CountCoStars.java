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
	
	public static class UniqueMapper extends Mapper<Object, Text, Text, IntWritable>{
		@Override
		public void map(Object key, Text value, Context context)
						throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			context.write(new Text(AuxFun.uniqueChars(split[1])),new IntWritable(1));
		}
	}
	
	public static class UniqueSetMapper extends Mapper<Object, Text, IntWritable, IntWritable>{
		@Override
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
			context.write(new IntWritable(AuxFun.uniqueChars(split[1]).length()),new IntWritable(1));
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
		
		Job job = Job.getInstance(new Configuration());
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapperClass(WholePassMapper.class);
		job.setReducerClass(SumReducer.class);
		
		FileInputFormat.setInputPaths(job, new Path(inputLocation));
		FileOutputFormat.setOutputPath(job, new Path(outputLocation));
		
		job.setJarByClass(CountCoStars.class);
		job.waitForCompletion(true);
	}	
}
