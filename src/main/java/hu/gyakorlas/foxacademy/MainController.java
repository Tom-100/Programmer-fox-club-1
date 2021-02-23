package hu.gyakorlas.foxacademy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Date;

@Controller
public class MainController {

    private Set<Fox> foxList = new TreeSet<Fox>();

    @RequestMapping("/")
    public String getIndex(@RequestParam(required = false) String name, Model model) {
        if(name == null){
            return "redirect:/login";
        }
        Fox tmp = findByName(name);
        String timeStamp = new SimpleDateFormat("yyyy. MMMM dd. HH:mm:ss").format(new Date());

        model.addAttribute("fox", tmp);

        return "index";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam(value = "name") String name, Model model) {
       Fox tmp = new Fox(name);

       foxList.add(tmp);

       model.addAttribute("name", tmp.getName());

       return "redirect:/?name=" + name;
    }

    @GetMapping("/nutritionStore")
    public String getNutritionStore(@RequestParam(value="name") String name, Model model){
        Fox tmp = findByName(name);
        List<String> foods = new ArrayList<>();
        List<String> drinks = new ArrayList<>();
        String timeStamp = new SimpleDateFormat("yyyy. MMMM dd. HH:mm:ss").format(new Date());

        foods.add("pizza");
        foods.add("bab leves");
        foods.add("káposztás paszuly");
        foods.add("túros tészta");
        foods.add("torta");
        drinks.add("lemonade");
        drinks.add("víz");
        drinks.add("kóla");
        drinks.add("sör");
        drinks.add("sprite");

        model.addAttribute("fox", tmp);
        model.addAttribute("foods", foods);
        model.addAttribute("drinks", drinks);

        return "nutritionStore";
    }

    @PostMapping("/nutritionStore")
    public String postNutrtitionStore(
            @RequestParam(value="name") String name,
            @RequestParam(value="food") String food,
            @RequestParam(value="drink") String drink,
            Model model){
        Fox tmp = findByName(name);
        String timeStamp = new SimpleDateFormat("yyyy. MMMM dd. HH:mm:ss").format(new Date());

        if(!tmp.getFood().equals(food)){
            tmp.getActionHistory().add(timeStamp + " : Food has been changed from: " + tmp.getFood() + " to: " + food);
        }
        tmp.setFood(food);

        if(!tmp.getDrink().equals(drink)){
            tmp.getActionHistory().add(timeStamp + " : Drink has been changed from: " + tmp.getDrink() + " to: " + drink);
        }
        tmp.setDrink(drink);

        model.addAttribute("fox", tmp);

        return "redirect:/?name=" + name;
    }

    @GetMapping("/trickCenter")
    public String getTrickCenter(
            @RequestParam(value="name") String name,
            @RequestParam(value="trick", required = false) String trick,
            Model model){
        Fox tmp = findByName(name);
        List<String> tricksToLearn = new ArrayList<>();
        int numberOfTricksLearned = tmp.getListOfTricks().size();
        String timeStamp = new SimpleDateFormat("yyyy. MMMM dd. HH:mm:ss").format(new Date());

        tricksToLearn.add("code in Java");
        tricksToLearn.add("write Html");
        tricksToLearn.add("learn Thymeleaf templates");
        tricksToLearn.add("play league of legends");

        if(trick != null){
            tmp.getListOfTricks().add(trick);
        }

        if(numberOfTricksLearned < tmp.getListOfTricks().size()){
            tmp.getActionHistory().add(timeStamp + " : Learned to: " + trick);
        }

        tricksToLearn.removeAll(tmp.getListOfTricks());

        model.addAttribute("fox", tmp);
        model.addAttribute("tricksToLearn", tricksToLearn);
        model.addAttribute("availableTricks", tricksToLearn.size());

        return "trickCenter";
    }

    @GetMapping("/actionHistory")
    public String getActionHistory(@RequestParam String name, Model model){
        Fox tmp = findByName(name);

        model.addAttribute("fox", tmp);

        return "actionHistory";
    }

    private Fox findByName(String name) {
        for (Fox f : foxList) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return new Fox(name);
    }
}
