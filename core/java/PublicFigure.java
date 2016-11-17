package facebook;

public class PublicFigure {
	private int Id_fb;
	private int Fans;
	private String Name;
	private ArrayList<Event> Events;
	private ArrayList<Like> Like;
	
	public PublicFigure(int id, int fan_count, String name, String jsonEvents, String likes) {
		this.Id_fb = id;
		this.Fans = fan_count;
		this.Name = name;
		setEvents(jsonEvents);
		setLikes(jsonLikes);
	}

	private setEvents(String jsonEvents) {
		Event event = new Event(jsonEvents);
		this.Events = event.getEvents();
	}	

	private setLikes(String jsonLikes) {
		Like like = new Like(jsonLikes);
		this.Likes = like.getLikes();
	}

	private int getID() {
		return this.Id_fb;
	}

	private int getFans() {
		return this.Fans;
	} 

	private String getName() {
		return this.Name;
	}
}