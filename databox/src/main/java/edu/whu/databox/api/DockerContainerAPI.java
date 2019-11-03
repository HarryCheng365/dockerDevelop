package edu.whu.databox.api;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Event;
import com.github.dockerjava.core.command.EventsResultCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DockerContainerAPI {

    DockerClient dockerClient;


    public void stop(String containerId) {
        dockerClient.stopContainerCmd(containerId).exec();
    }

    public void start(String containerId) {
        dockerClient.startContainerCmd(containerId).exec();
    }

    public void wait(String containerId, ResultCallback callback){
        dockerClient.waitContainerCmd(containerId).exec(callback);
        EventsResultCallback callback = new EventsResultCallback() {
            @Override
            public void onNext(Event event) {
                System.out.println("Event: " + event);
                super.onNext(event);
            }
        };

        dockerClient.eventsCmd().exec(callback).awaitCompletion().close();
    }



}
