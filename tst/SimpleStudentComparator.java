
import java.util.Comparator;

public class SimpleStudentComparator implements Comparator<SimpleStudent>{

	@Override
	public int compare(SimpleStudent o1, SimpleStudent o2) {
		return o1.id - o2.id  ;
	}

}
