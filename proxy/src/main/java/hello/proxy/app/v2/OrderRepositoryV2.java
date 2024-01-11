package hello.proxy.app.v2;

public class OrderRepositoryV2 {
    public void save(String itemId) {
        if (itemId.equals("ex")) {
            throw new IllegalStateException("exception!!");
        }
        sleep(1000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}