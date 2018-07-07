package neige_i.mynews.view;

import android.content.Context;
import android.content.res.Resources;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TopicAdapterTest {

    @Mock private Context mContext;
    @Mock private Resources mResources;

    @Test
    public void getPageTitle() {
        // Initialize the mocked objects
        MockitoAnnotations.initMocks(this);

        // Initialize the String array that represents the 'tab_title' resources
        String[] stringArray = new String[]{"one", "two", "three"};

        // Initialize the method calls of mocked objects
        when(mContext.getResources()).thenReturn(mResources);
        when(mResources.getStringArray(anyInt())).thenReturn(stringArray);

        // Check if the 'getPageTitle' method returns the correct value
        makeAssertions(stringArray);
    }

    /**
     * Checks if the page title matches the page position.
     * @param strings The String array that contains the titles.
     */
    private void makeAssertions(String[] strings) {
        TopicAdapter topicAdapter = new TopicAdapter(null, mContext);
        for (int i = 0; i < strings.length; i++)
            assertEquals(strings[i], topicAdapter.getPageTitle(i));
    }
}