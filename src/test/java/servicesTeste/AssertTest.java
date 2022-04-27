package servicesTeste;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {
	
	@Test
	public void testTrueFalse() {	
		Assert.assertTrue(true);;
		Assert.assertFalse(false);	
	}
	
	@Test
	public void testEqualsNumber() {
		Assert.assertEquals("Erro de comparaçao",1, 1);
		Assert.assertEquals(0.51, 0.51, 0.01);// the last element is the margin of error
		
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals("Erro de comparaçao",Integer.valueOf(i), i2); //pass variable i to object
		Assert.assertEquals(i, i2.intValue());//pass variable i2 to int
	}
	
	@Test
	public void testEqualsString() {
		Assert.assertEquals("Erro de comparaçao","bola", "bola");//validates if they are strictly equals
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));// validates if they are the same word ignoring uppercase and lowercase letters
		Assert.assertTrue("bola".startsWith("bo"));//validates if the word starts with the given prefix
	}
	
	@Test
	public void testObject() {
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Usuario u3 = u2; 
		
		Assert.assertEquals(u1, u2);//to do the validation you need the equals method in the object class
		Assert.assertSame(u3, u2);//checks if the two objects are from the same instance
		
		
	}

}
