Batalha RPG - Simulador de Combate
Simulador de combate por turnos desenvolvido em Java com interface Swing e testes automatizados em JUnit 5.
Configuração e Testes (JUnit Jupiter)
Para habilitar os testes e compilar o projeto corretamente, é necessário que o usuario tenha o JAR do JUnit 5 (junit-platform-console-standalone.jar) no classpath.
como compilar
Bash
# Windows
javac -cp ".;junit-platform-console-standalone.jar" *.java

# Linux/Mac
javac -cp ".:junit-platform-console-standalone.jar" *.java
como rodar
Bash
java -jar junit-platform-console-standalone.jar --class-path . --select-class TesteJunit5

Estrutura do Código
O coração do sistema está na classe abstrata Combatente, que define atributos protegidos essenciais (nome, nível, PV) e serve de base para herança. Dela derivam três especializações:
Guardião: alta defesa (150 PV), mecânica de bloqueio com vigor;
Arcanista: 80 PV, usa mana para ataques mágicos potentes;
Caçador: 90 PV, 40% de chance de dano crítico (×2).

A classe Arena gerencia a batalha: separa as equipes em ArrayLists (Aliança e Horda), embaralha a ordem de ação a cada turno com Collections.shuffle e usa polimorfismo no processamento: chama métodos de ataque e dano de forma genérica, deixando o Java executar a implementação específica de cada subclasse automaticamente.
É uma arquitetura limpa que combina abstração, herança e polimorfismo para criar um sistema flexível e extensível.

3. Interface e Execução
Main.java: Inicia a aplicacao via SwingUtilities.
InterfaceSwing.java: Permite configurar a quantidade de herois e o nivel de dificuldade de cada lado antes de iniciar o combate.
O arquivo TesteJunit5.java valida:
Criacaoo: se o HP inicial e nome estao corretos
Ataque: Se o dano calculado respeita os valores base ou crítico
Dano: Se a subtração de pontos de vida funciona adequadamente
