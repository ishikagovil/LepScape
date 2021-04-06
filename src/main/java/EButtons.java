public enum EButtons {
	PLANT("plant"),
	COMPOST("compost"), 
	DOWNLOAD("download"),
	SECTIONING("sectioning"),
	PLOTTING("plotting");
	
	private String buttonName = null;
	private EButtons(String buttonName) {this.buttonName = buttonName;}
	public String getButton() {return buttonName;}
}
