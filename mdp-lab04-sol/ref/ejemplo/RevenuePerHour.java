package ejemplo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class RevenuePerHour {
	public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 4) {
			System.err.println("Usage: WordCount <in1> <in2> <in3> <tmp1> <tmp2> <out>");
			System.exit(2);
		}
		
		Job job1 = Job.getInstance(new Configuration());
		MultipleInputs.addInputPath(job1, new Path(otherArgs[0]),
                TextInputFormat.class, ReceiptItemsMapper.class);
        MultipleInputs.addInputPath(job1, new Path(otherArgs[1]),
                TextInputFormat.class, ReceiptTimesMapper.class);
	    FileOutputFormat.setOutputPath(job1, new Path(otherArgs[3]));
	    
        job1.setReducerClass(ItemsTimesReducer.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(Text.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(Text.class);
        job1.waitForCompletion(true);
        
		Job job = Job.getInstance(new Configuration());
		MultipleInputs.addInputPath(job, new Path(otherArgs[2]),
                TextInputFormat.class, ItemsTimesMapper.class);
        MultipleInputs.addInputPath(job, new Path(otherArgs[3]),
                TextInputFormat.class, ItemsPricesMapper.class);
	    FileOutputFormat.setOutputPath(job, new Path(otherArgs[4]));
	    
        job.setReducerClass(TimesPricesReducer.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        job.waitForCompletion(true);
        
        
        
        job.setNumReduceTasks(1);
        
        
    }
	
	public static class ReceiptTimesMapper extends Mapper<Object, Text, Text, Text> {
		
	}
	
	public static class ReceiptItemsMapper extends Mapper<Object, Text, Text, Text> {
		
	}
	
	public static class ItemsTimesReducer extends Reducer<Text, Text, Text, Text> {
		
	}
	
	public static class ItemsTimesMapper extends Mapper<Object, Text, Text, Text> {
		
	}
	
	public static class ItemsPricesMapper extends Mapper<Object, Text, Text, Text> {
		
	}	
	
	public static class TimesPricesReducer extends Reducer<Text, Text, Text, LongWritable> {
		
	}	
	
	public static class HoursRevenueReducer extends Reducer<Text, Text, Text, LongWritable> {
		
	}

}
