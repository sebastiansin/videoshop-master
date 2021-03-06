/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package videoshop.catalog;

import static org.salespointframework.core.Currencies.EURO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Metric;

import videoshop.catalog.Disc.DiscType;

//(｡◕‿◕｡)
// Da der Shop DVD sowie BluRay verkaufen soll ist es sinnvoll eine gemeinsame Basisklasse zu erstellen.
// Diese erbt von Product um die Catalog-Klasse aus Salespoint nutzen zu können.
// Ein Primärschlüssel ist nicht notwendig, da dieser schon in Product definiert ist, alle anderen JPA-Anforderungen müssen aber erfüllt werden
@Entity
public class Disc extends Product {
	
	public static float rabattt(String raba) {
		float x=0;
		float y = Float.parseFloat(raba);
		
		x= y/100;
		
		return x;
	}
	

	public static enum DiscType {
		BLURAY, DVD;
	}

	// (｡◕‿◕｡)
	// primitve Typen oder Strings müssen nicht extra für JPA annotiert werden
	private String genre, image;
	private DiscType type;
	private int rabatt;
	

	// (｡◕‿◕｡)
	// Jede Disc besitzt mehrere Kommentare, eine "1 zu n"-Beziehung -> @OneToMany für JPA
	// cascade gibt an, was mit den Kindelementen (Comment) passieren soll wenn das Parentelement (Disc) mit der Datenbank
	// "interagiert"
	@OneToMany(cascade = CascadeType.ALL) //
	private List<Comment> comments = new ArrayList<>();

	private Money price;
	private Money pricee;
	



	@SuppressWarnings("unused")
	private Disc() {}

	public Disc(String name, String image, Money price, String genre, DiscType type, int rabatt, Money pricee) {
		
		
		super(name, pricee );
		
		pricee = price;
		
		this.price = price;
		this.pricee = pricee;
		this.setRabatt(rabatt);
		this.image = image;
		this.genre = genre;
		this.type = type;
	}

	public String getGenre() {
		return genre;
	}

	public void addComment(Comment comment) {
		comments.add(comment);
		
	}
	
	/*
	public void setRabatt(String rabatt){
		this.rabatt = rabatt;
		
		float x=0;
		float y = Float.parseFloat(rabatt);
		
		x= y/100;
		
		this.price = pricee.subtract(pricee.multiply(x));
	}
*/


	// (｡◕‿◕｡)
	// Es ist immer sinnvoll sich zu überlegen wie speziell der Rückgabetyp sein sollte
	// Da sowies nur über die Kommentare iteriert wird, ist ein Iterable<T> das sinnvollste.
	// Weil wir keine Liste zurück geben, verhindern wir auch, dass jemand die comments-Liste einfach durch clear() leert.
	// Deswegen geben auch so viele Salespoint Klassen nur Iterable<T> zurück ;)
	public Iterable<Comment> getComments() {
		return comments;
	}
	
	public Money getPrice() {
		return price;
	}

	public String getImage() {
		return image;
	}
	/*
	public String getRabatt() {
		return rabatt;
	}
	*/

	public DiscType getType() {
		return type;
	}

	public int getRabatt() {
		return rabatt;
	}

	public void setRabatt(int rabatt) {
		this.rabatt = rabatt;
	}
	

	
}
