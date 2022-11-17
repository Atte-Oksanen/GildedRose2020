package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	@Test
	public void exampleTest() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();
		
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert quality has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
	}
	
	@Test
	public void testMain() {
		GildedRose inn = new GildedRose();
		List<Item> testItems = new ArrayList<Item>();
		testItems.add(new Item("+5 Dexterity Vest", 10, 20));
		testItems.add(new Item("Aged Brie", 2, 0));
		testItems.add(new Item("Elixir of the Mongoose", 5, 7));
		testItems.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		testItems.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		testItems.add(new Item("Conjured Mana Cake", 3, 6));
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		String[] args = null;
		inn.main(args);
		assertEquals("The print statement is not correct","OMGHAI!\n", out.toString());
		
		for(int n = 0; n < testItems.size(); n++) {
			assertEquals("The objects do not match", testItems.get(n).getName(), inn.getItems().get(n).getName());
		}
		assertEquals("The quality is not correct", 1, inn.getItems().get(1).getQuality());
	}
	
	@Test
	public void inceasedValueTest() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Aged Brie", 0, 0));
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 0));
		for(int n = 0; n < 27 ; n++) {
			inn.oneDay();
			switch (n){
			case 4:
				assertEquals("The quality of Backstage passes should only increase by 1 before sellin is under 10", 5, inn.getItems().get(2).getQuality());
				inn.getItems().get(2).setQuality(49);
				break;
			case 6:
				assertEquals("The quality should never be more than 50", 50, inn.getItems().get(2).getQuality());
				inn.getItems().get(2).setQuality(30);
				break;
			case 10:
				assertEquals("The quality should increase by 3", 39, inn.getItems().get(2).getQuality());
				break;
			}
			
		}
		assertEquals("The quality of Backstage passes should be 0 after the concert", 0, inn.getItems().get(2).getQuality());
		assertEquals("The quality of Sulfuras never changes", 80, inn.getItems().get(1).getQuality());
		assertEquals("The quality can never be more than 50", 50, inn.getItems().get(0).getQuality());
	}
	@Test
	public void TestDecreasingPrices() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 5, 20));
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		inn.setItem(new Item("Conjured Mana Cake", 10, 50));
		
		for(int n = 0; n < 15; n++) {
			inn.oneDay();
			switch(n) {
			case 4:
				assertEquals("The quality should have dropped by 5 in 5 days", 15, inn.getItems().get(0).getQuality());
				break;
			case 9:
				assertEquals("The quality should be 5", 5, inn.getItems().get(0).getQuality());
			}
		}
		assertEquals("The quality should never be under 0", 0, inn.getItems().get(0).getQuality());
		assertEquals("The quality of Sulfuras never changes", 80, inn.getItems().get(1).getQuality());
		assertEquals("The quality should be 30", 30, inn.getItems().get(2).getQuality());
	}
	@Test
	public void backStagePassTest() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 4, 49));
		inn.oneDay();
		assertEquals("The quality can never be more than 50", 50, inn.getItems().get(0).getQuality());
	}
}
