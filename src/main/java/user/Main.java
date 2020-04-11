package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;


import static user.User.Gender.MALE;

public class Main {

    public static void main(String[] args) {
        User user = User.builder()
                .name("James Bond")
                .username("007")
                .password("pw")
                .email("jamesbond@gmail.com")
                .gender(MALE)
                .enabled(true)
                .dob(LocalDate.parse("1920-11-11"))
                .build();

        User user2 = User.builder()
                .name("Sadio Mané")
                .password("liverpool")
                .username("sm10")
                .email("mane@gmail.com")
                .gender(MALE)
                .enabled(true)
                .dob(LocalDate.parse("1992-04-10"))
                .build();

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try(Handle handle = jdbi.open()){
            UserDAO dao = handle.attach(UserDAO.class);
            dao.createTable();
            dao.insert(user);
            dao.insert(user2);
            dao.findById(1).stream().forEach(System.out::println);
            dao.findByUsername("sm10").stream().forEach(System.out::println);
            dao.delete(user);
            dao.list().stream().forEach(System.out::println);
        }
    }
}
