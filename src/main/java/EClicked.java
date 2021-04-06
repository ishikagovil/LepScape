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
	//Pop ups
	PLANT("plant"),
	COMPOST("compost"), 
	DOWNLOAD("download"),
	SECTIONING("sectioning"),
	PLOTTING("plotting");
	private String nextName = null;
	private EClicked(String pageName) {this.nextName = pageName;}
	public String getPage() {return nextName;}
}