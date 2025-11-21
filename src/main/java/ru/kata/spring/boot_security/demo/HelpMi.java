//пояснения для себя

//lamboc это пока не стал делать там пол проекта положил он мне устал чистить его дела  !!!


//!!!!!!!!!!!!!!!!!!!!!!!
//правка важно то есть юзер сервис работает со своим слоем дао !
//но и может работать с сервис кар например но не кар дао
//!!!!!!!!!!!!!!!!!!!!



//UserDetailsServiceImpl это как мост
//
//
//
//UserService ← ваш сервис
//User ← ваша сущность
//Role ← ваша сущность
//UserDao/Repository ← доступ к данным

//WebSecurityConfig это основной файл для настройки секюрности хД
//
//        основные моменты
//1.. Конфигурация через наследование spring Security использует шаблон "Adapter" для настройки.
//Мы переопределяем методы для кастомизации. это паттерн проектирования!! ссылку Тимур кидал в лс !!!
//
//    2-Декларативная безопасность
//        .antMatchers("/admin/**").hasRole("ADMIN")
//        .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
// ЧТО защищать, а не КАК - фреймворк сам реализует механизм.


//Chain of Responsibility (Цепочка обязанностей)
//    пример !!
//
//
//
//@Override
//protected void configure(HttpSecurity http) throws Exception {
//    http.authorizeRequests()
//            .antMatchers("/admin/**").hasRole("ADMIN")     // КТО имеет доступ
//            .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//            .anyRequest().authenticated()                  // все остальные требуют аутентификации
//            .and()
//            .formLogin()                                   // КАК входить
//            .loginPage("/login")                           // страница логина
//            .successHandler(successUserHandler)            // куда перенаправлять после входа
//            .permitAll()
//            .and()
//            .logout()                                      // КАК выходить
//            .logoutUrl("/logout")
//            .logoutSuccessUrl("/login?logout");
//}


//цепочка
//
//HTTP Request → Security Filters → Authentication? → No → /login
//                                     ↓ Yes
//                               Check Authorities → Has role? → No → 403
//                                     ↓ Yes
//                                 Your Controller


//тот класс - главный дирижер безопасности, который:
//
//Определяет правила доступа
//
//Настраивает процесс аутентификации
//
//Управляет фильтрами безопасности
//
//Интегрирует кастомные компоненты (SuccessHandler, UserDetailsService)