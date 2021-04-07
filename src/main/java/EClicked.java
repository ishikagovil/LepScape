public enum EClicked {
	//Pages
	CONDITIONSCREEN("ConditionScreen"),
	GALLERY("Gallery"),
	GARDENDESIGN("GardenDesign"),
	NAVIGATION("Navigation"),
	PLOTDESIGN("PlotDesign"),
	START("Start"),
	SUMMARY("Summary"),
	LEPEDIA("Lepedia"),
	LEFTOFF("Leftoff"),
	//Pop ups
	LOAD("load"),
	PLANT("plant"),
	COMPOST("compost"), 
	DOWNLOAD("download"),
	SECTIONING("sectioning"),
	PLOTTING("plotting");
	private String nextName = null;
	private EClicked(String nextName) {this.nextName = nextName;}
	public String getPage() {return nextName;}
}