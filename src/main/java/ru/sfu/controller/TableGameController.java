package ru.sfu.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sfu.model.TableGame;
import ru.sfu.repository.TableGameRepository;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/table_games")
public class TableGameController {
    private final TableGameRepository repo;

    public TableGameController(TableGameRepository repo) {
        this.repo = repo;
    }

    @GetMapping()
    public String menu(){
        return "table_games/menu";
    }

    @GetMapping("/{id}")
    public String showGame(@PathVariable("id") int id, Model model){
        Optional<TableGame> tg = repo.findById(id);
        if (tg.isPresent()) {
            model.addAttribute("game", tg.get());
            return "table_games/showGame";
        }

        return "redirect:/table_games";
    }

    @GetMapping("/show")
    public String showGames(Model model){
        model.addAttribute("games", repo.findAll());
        return "table_games/show";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String newGame(Model model){
        model.addAttribute("game", new TableGame());
        return "table_games/add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addGame(@ModelAttribute("game") @Valid TableGame game,
                          BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "table_games/add";
        }

        repo.save(game);
        return "redirect:/table_games";
    }



    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editGame(@PathVariable("id") int id, Model model){
        Optional<TableGame> tg = repo.findById(id);
        if (tg.isPresent()){
            model.addAttribute("game", tg.get());
            return "table_games/edit";
        }


        return "redirect:/table_games";
    }

    @PostMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateGame(@PathVariable("id") int id,
                             @ModelAttribute("game") @Valid TableGame game,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "table_games/edit";
        }
        repo.save(game);
        return "redirect:/table_games";
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteGame(@PathVariable("id") int id){
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
        return "redirect:/table_games";
    }

    @GetMapping("search")
    public String findGame(@RequestParam("price") int maxPrice, Model model)
    {
        model.addAttribute("games", repo.findAllByPriceIsLessThanEqual(maxPrice));

        return "/table_games/show";
    }

    @GetMapping("find")
    public String showFoundedGames(){
        return "table_games/find";
    }
}
