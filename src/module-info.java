module utopia
{
	requires java.sql;
	requires org.junit.jupiter.api;
	requires junit;
	exports com.ss.utopia.test.dal to junit;
}