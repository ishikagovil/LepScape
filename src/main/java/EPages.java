public enum EPages {
	CONDITIONSCREEN("ConditionScreen"),
	GALLERY("Gallery"),
	GARDENDESIGN("GardenDesign"),
	NAVIGATION("Navigation"),
	PLOTDESIGN("PlotDesign"),
	START("Start"),
	SUMMARY("Summary"),
	LEPEDIA("Lepedia");
	private String pageName = null;
	private EPages(String pageName) {this.pageName = pageName;}
	public String getPage() {return pageName;}
}