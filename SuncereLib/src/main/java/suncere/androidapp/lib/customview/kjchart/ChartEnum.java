package suncere.androidapp.lib.customview.kjchart;

public enum ChartEnum {
	LineChart(0),
	BarChart(1),
	PercentageBaChart(2);

	ChartEnum(int value)
	{
		this.value=value;
	}

	public int Value()
	{
		return value;
	}

	private int value;
}
