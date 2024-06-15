package TTCS.IdentityService.application.Command.Aggregate;

import TTCS.IdentityService.application.Command.CommandService.DTO.OTPResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class FutureTracker<T> {
    public static final Map<String, CompletableFuture<String>> futures = new ConcurrentHashMap<>();

    public static final Map<String, CompletableFuture<OTPResponse>> futuresOTP= new ConcurrentHashMap<>();


//    public static void addFuture(String id, CompletableFuture<String> future) {
//        futures.put(id, future);
//    }
//
//    public static CompletableFuture<String> getFuture(String id) {
//        return futures.get(id);
//    }
//
//    public static CompletableFuture<String> removeFuture(String id) {
//        return futures.remove(id);
//    }
}
