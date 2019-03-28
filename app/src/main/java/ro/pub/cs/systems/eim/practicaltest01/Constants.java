package ro.pub.cs.systems.eim.practicaltest01;

public interface Constants {
    String LEFT_EDIT_TEXT = "leftEditText" ;
    String RIGHT_EDIT_TEXT = "rightEditText" ;
    String NUMBER_CLICKS = "numberOfClicksTextView" ;
    int SECONDARY_ACTIVITY_REQUEST_CODE = 127 ;

    final public static String FIRST_NUMBER = "firstNumber";
    final public static String SECOND_NUMBER = "secondNumber";

    final public static String[] actionTypes = {
            "ro.pub.cs.systems.eim.practicaltest01.arithmeticmean",
            "ro.pub.cs.systems.eim.practicaltest01.geometricmean"
    };

    final public static int NUMBER_OF_CLICKS_THRESHOLD = 5;
    final public static int SERVICE_STOPPED = 0;
    final public static int SERVICE_STARTED = 1;

    final public static String PROCESSING_THREAD_TAG = "[Processing Thread]";

    final public static String BROADCAST_RECEIVER_EXTRA = "message";
    final public static String BROADCAST_RECEIVER_TAG = "[Message]";
}
