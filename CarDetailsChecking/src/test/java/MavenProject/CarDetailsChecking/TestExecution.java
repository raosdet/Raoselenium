package MavenProject.CarDetailsChecking;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class TestExecution {

@Test
public void Tests() throws Exception {
	CarCheck2 car = new CarCheck2();
	car.carDetailsCompare();
}
@AfterTest
public void TestClose() throws Exception {
	UserDefinedFunctions udf = new UserDefinedFunctions();
	udf.CloseWebsite();
}
}
