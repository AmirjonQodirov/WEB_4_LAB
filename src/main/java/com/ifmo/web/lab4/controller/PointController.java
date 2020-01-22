package com.ifmo.web.lab4.controller;

import com.ifmo.web.lab4.model.data.PointForClient;
import com.ifmo.web.lab4.repositories.PointRepository;
import com.ifmo.web.lab4.repositories.UserRepository;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ifmo.web.lab4.model.Graphic;
import com.ifmo.web.lab4.model.data.Point;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@URL
public class PointController {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final Graphic graphic;

    @Autowired
    PointController(PointRepository pointRepository, UserRepository userRepository, Graphic graphic) {
        this.pointRepository = pointRepository;
        this.graphic = graphic;
        this.userRepository = userRepository;
    }

    @CrossOrigin
    @GetMapping("/points")
    Collection<PointForClient> allPoints(Principal user) {
        System.out.println("all points request from "+user.getName());
        List<PointForClient> pointsForClient = new ArrayList<>();
        Collection<Point> points = pointRepository.findAllUserPoints(userRepository.findByUsername(user.getName()));
        for (Point p : points) {
            pointsForClient.add(new PointForClient(p.getX(), p.getY(), p.getR(), p.getResult()));
        }
        return pointsForClient;
    }

    @CrossOrigin
    @PostMapping("/points")
    Point newPoint(@RequestBody Point newPoint, Principal user) {
        newPoint.setResult(graphic.isInArea(newPoint));
        newPoint.setUser(userRepository.findByUsername(user.getName()));
        return pointRepository.save(newPoint);
    }

    @CrossOrigin
    @GetMapping("/points/{r}")
    Collection<PointForClient> allPointsRecalculation(@PathVariable Double r, Principal user) {
        List<PointForClient> recalculatedPoints = new ArrayList<>();
        Collection<Point> points = pointRepository.findAllUserPoints(userRepository.findByUsername(user.getName()));

        for (Point p : points) {
            PointForClient point = new PointForClient(p.getX(), p.getY(), r, false);
            point.setResult(graphic.isInArea(point));
            recalculatedPoints.add(point);
        }

        return recalculatedPoints;
    }

//    @CrossOrigin
//    @PutMapping("/points/{id}")
//    Point replacePoint(@RequestBody Point newPoint, @PathVariable Long id) {
//
//        return pointRepository.findById(id)
//                .map(point -> {
//                    point.setX(newPoint.getX());
//                    point.setY(newPoint.getY());
//                    point.setR(newPoint.getR());
//                    point.setInArea(graphic.isInArea(newPoint));
//                    return pointRepository.save(point);
//                })
//                .orElseGet(() -> {
//                    newPoint.setInArea(graphic.isInArea(newPoint));
//                    newPoint.setId(id);
//                    return pointRepository.save(newPoint);
//                });
//    }

//    @CrossOrigin
//    @DeleteMapping("/points/{id}")
//    void deletePoint(@PathVariable Long id) {
//        pointRepository.deleteById(id);
//    }
}
