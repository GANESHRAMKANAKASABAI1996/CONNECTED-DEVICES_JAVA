package schooldomain.studentname.connecteddevices.common;

/**
 * Class sensorData is used to capture the data and print the current value,minimum value
 * maximum value and average value of the data
 */

public class SensorData {
	private Double curVal;
	private Double maxVal;
	private Double minVal;
	private Double totVal;
	private Double diffVal;
	private Double avgVal;
	private String time;
	private Integer sampleCount = 0;
	private String name;
	/**
	 * parameterized constructor
	 * @param maxVal indicates the maximum value
	 * @param minVal indicates the minimum value
	 * @param time indicates the time of capturing data
	 * @param name indicates the name of the parameter like temperature,pressure etc..
	 */
	
	public SensorData(Double maxVal, Double minVal, String time, String name) {
		super();
		this.maxVal = maxVal;
		this.minVal = minVal;
		this.time = time;
		this.name = name;
	}
	
	
	/**
	 * This method returns the sample count
	 * @return sample count
	 */
	
	public Integer getSampleCount() {
		return sampleCount;
	}

    /**
     * @param sampleCount indicates the count of the iteration
     */

	public void setSampleCount(Integer sampleCount) {
		this.sampleCount = sampleCount;
	}

    /**
     * This method returns the average value
     * @return avgVal
     */

	public Double getAvgVal() {
		return avgVal;
	}

    /**
     * This method sets the average value
     * @param avgVal indicates the calculated average value
     */

	public void setAvgVal(Double avgVal) {
		this.avgVal = avgVal;
	}
	/**
	 * This method is used to return the name
	 * @return name
	 */


	public String getName() {
		return name;
	}
	/**
	 * This method is used set name to achieve encapsulation
	 * @param name indicates the name of the parameter 
	 */

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * This method returns the current value
	 * @return curVal
	 */

	public Double getCurVal() {
		return curVal;
	}
	
	/**
	 * This method is used to set the current value
	 * @param curVal indicates the current value
	 */
	public void setCurVal(Double curVal) {
		this.curVal = curVal;
	}
	
	/**
	 * generating getters and setters for the data members which are declared as private
	 * so that the data members could be accessed outside the class
	 */
	
	public Double getMaxVal() {
		return maxVal;
	}
	
	public void setMaxVal(Double maxVal) {
		this.maxVal = maxVal;
	}
	
	public Double getMinVal() {
		return minVal;
	}
	
	public void setMinVal(Double minVal) {
		this.minVal = minVal;
	}
	
	public Double getTotVal() {
		return totVal;
	}
	
	public void setTotVal(Double totVal) {
		this.totVal = totVal;
	}
	
	public Double getDiffVal() {
		return diffVal;
	}
	
	public void setDiffVal(Double diffVal) {
		this.diffVal = diffVal;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	/**
	 * toString method is overridden to customize the String representation of an object
	 */
	
	@Override
	public String toString()
	{
		String str = "Current Value: "+this.getCurVal()+"\nTime: "+this.getTime()+"\nAverage Value: "+this.getAvgVal()+"\nMinimum Value: "+this.getMinVal()+"\nMaximum Value: "+this.getMaxVal();
		return str;
		
	}
	/**
	 * This method is used to add the value to the current value
	 */
	
	public void addValue()
	{
		this.setSampleCount(this.getSampleCount()+1);
		
	}
	
	
}
