
public class MediaItem {

	private String title;
	private String format;
	private boolean onLoan;
	private String loanedTo;
	private String dateLoaned;

	public MediaItem() {
		this.title = null;
		this.format = null;
		this.onLoan = false;
		this.loanedTo = null;
		this.dateLoaned = null;

	}

	public MediaItem(String title, String format) {
		this.title = title;
		this.format = format;
		this.onLoan = false;
		this.loanedTo = null;
		this.dateLoaned = null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean getOnLoan() {
		return onLoan;
	}

	public void setOnLoan(boolean onLoan) {
		this.onLoan = onLoan;
	}

	public String getLoanedTo() {
		return loanedTo;
	}

	public void setLoanedTo(String loanedTo) {
		this.loanedTo = loanedTo;
	}

	public String getDateLoaned() {
		return dateLoaned;
	}

	public void setDateLoaned(String dateLoaned) {
		this.dateLoaned = dateLoaned;
	}

	public void markOnLoan(String name, String date) {
			setLoanedTo(name);
			setDateLoaned(date);
			setOnLoan(true);
	}

	public void markReturned(){
		onLoan = false;
		setDateLoaned(null);
		setLoanedTo(null);
		setDateLoaned(null);
	}
}
