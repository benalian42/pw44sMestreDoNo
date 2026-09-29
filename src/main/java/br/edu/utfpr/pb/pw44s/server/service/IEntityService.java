/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.edu.utfpr.pb.pw44s.server.service;

import br.edu.utfpr.pb.pw44s.server.model.Category;
import java.util.List;
/**
 *
 * @author Aluno
 */
public interface IEntityService {
    List<Category> findAll();
    Category findById(Long id);
    void deleteById(Long id);
    boolean exists(Long id);
    long count( );
}