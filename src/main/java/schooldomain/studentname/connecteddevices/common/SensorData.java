/**
 * Class which stores Sensor variables of Raspberry pi
 */
package schooldomain.studentname.connecteddevices.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author GANESHRAM KANAKASABAI
 *
 */
public class SensorData {
	private Double curVal;
	private Double maxVal;
	private Double minVal;
	private Double totVal;
	private Double diffVal;
	private Double avgVal;
	private String timeStamp;
	private Integer sampleCount = 0;
	private String name;
	
	/*
	 * Constructor to initialize a Sensor Data object
	 * @param maxVal = Maximum sensor Value
	 * @param minVal = Minimum sensor Value
	 * @param   time =  time
	 * @param   name =  sensor name
	 */
	public SensorData(Double maxVal, Double minVal, String time, String name) {
		super();
		this.maxVal = maxVal;
		this.minVal = minVal;
		this.timeStamp = time;
		this.name = name;
		this.totVal = 0.00;
	}
	
	/**
	 * This getter method to get the SampleCount value
	 * @return: sampleCount
	 */
	public Integer getSampleCount() {
		return sampleCount;
	}
	
	/**
	 * This setter method is used to store the sampleCount value
	 * @param sampleCount indicates the sampleCount value
	 */
	public void setSampleCount(Integer sampleCount) {
		this.sampleCount = sampleCount;
	}
	
	/**
	 * This getter method is used to return the sample average value
	 * @return avgVal
	 */
	public Double getAvgVal() {
		return avgVal;
	}
	
	/**
	 * This setter method is used to store the average value
	 * @param avgVal indicates the average value
	 */
	public void setAvgVal(Double avgVal) {
		this.avgVal = avgVal;
	}
	
	/**
	 * This getter method is used to return the name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This setter method is used to store the name
	 * @param name indicates the current name 
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This getter method is used to return the current value
	 * @return name
	 */
	public Double getCurVal() {
		return curVal;
	}
	
	/**
	 * This setter method is used to store the current value
	 * @param name indicates the current value
	 */
	public void setCurVal(Double f) {
		this.curVal = f;
	}
	
	/**
	 * This getter method is used to return the maximum value
	 * @return maxVal
	 */
	public Double getMaxVal() {
		return maxVal;
	}
	
	/**
	 * This setter method is used to store the maximum value
	 * @param name indicates the maximum value
	 */
	public void setMaxVal(Double maxVal) {
		this.maxVal = maxVal;
	}
	
	/**
	 * This getter method is used to return the minimum value
	 * @return minVal
	 */
	public Double getMinVal() {
		return minVal;
	}
	
	/**
	 * This setter method is used to store the minimum value
	 * @param name indicates the minimum value
	 */
	public void setMinVal(Double minVal) {
		this.minVal = minVal;
	}
	
	/**
	 * This getter method is used to return the total value
	 * @return totVal
	 */
	public Double getTotVal() {
		return totVal;
	}
	
	/**
	 * This setter method is used to store the total value
	 * @param name indicates the total value
	 */
	public void setTotVal(Double totVal) {
		this.totVal = totVal;
	}
	
	/**
	 * This getter method is used to get the difference of average value and current value
	 * @return diffVal
	 */
	public Double getDiffVal() {
		return diffVal;
	}
	
	/**
	 * This setter method is used to set the difference of average value and current value
	 * @param diffVal indicates the difference of the average value and the current value
	 */
	public void setDiffVal(Double diffVal) {
		this.diffVal = diffVal;
	}
	
	/**
	 * This getter method is used to get the current time
	 * @return time
	 */
	public String getTime() {
		return this.timeStamp;
	}
	
	/**
	 * This setter method is used to set the current time 
	 * @param time indicates the current time
	 */
	public void setTime(String time) {
		this.timeStamp = time;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * function to present the object in customized human readable form
	 */
	@Override
	public String toString()
	{
		String str = "Current Value: "+this.getCurVal()+"\nTime: "+this.getTime()+"\nAverage Value: "+this.getAvgVal()+"\nMinimum Value: "+this.getMinVal()+"\nMaximum Value: "+this.getMaxVal();
		return str;
		
	}
	
	/*
	 * Updates the timestamp to current time
	 */
	public void updateTimeStamp() {
		this.timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm.ss").format(new Date());
	}
	
	/*
	 * Function adds current value to calculate average value
	 * @param val - Current sensor value
	 */
	public void updateValue(float val) {
		updateTimeStamp();
		++this.sampleCount;
		this.curVal = (double) val;
		this.totVal += val;
		if (this.curVal < this.minVal) {
			this.minVal = this.curVal;
		}
		if (this.curVal > this.maxVal) {
			this.maxVal = this.curVal;
		}
		if (this.totVal != 0 && this.sampleCount > 0) {
			this.avgVal = this.totVal / this.sampleCount;
		}
	}
	
}

 