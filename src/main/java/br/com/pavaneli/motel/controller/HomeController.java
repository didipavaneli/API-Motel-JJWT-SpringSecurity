
package br.com.pavaneli.motel.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.pavaneli.motel.entity.Quarto;
import br.com.pavaneli.motel.repository.QuartoRepository;

@Controller
public class HomeController {

    @Autowired
    private QuartoRepository quartoRepository;

    @GetMapping("/home")
    public String home(Model model) {
        List<Quarto> quartos = quartoRepository.findAll();
        quartos.sort(Comparator.comparing(Quarto::getNumero));
        model.addAttribute("quartos", quartos);
        return "home";
    }
}