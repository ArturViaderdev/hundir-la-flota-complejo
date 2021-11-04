/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flota2p;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author arturv
 */
public class Flota2p {

    /**
     * @param args the command line arguments
     */
   static char[][]tablero;
   static char[][]tabjugador;
   static char[][]tabmio;
   static char [][]tabjugadorma;
   static final int GRANDEX=10;
   static final int GRANDEY=10;
   static int contbb = 2;
   static int contbc = 3;
   static int contbd = 4;
   static int contbe = 5;
    public static void main(String[] args) {
      inicializa();
      System.out.print("\033[H\033[2J");
      System.out.flush();
      mostrartablero();
      jugar();
    }

    private static void jugar()
    {
      BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
      int maxjugadas = (GRANDEX*GRANDEY)/2, contjugadas=0,juegox,juegoy;

      String entrada;
      boolean sal = false;
      try
      {
        while(!sal)
        {
          if(contjugadas<maxjugadas)
          {
            do {
              System.out.println("Introduce coordernadas fila,columna o X para salir.");
              entrada = lector.readLine();

            } while (!entradacorrecta(entrada));
            if(entrada.charAt(0)=='X')
            {
              sal=true;
            }
            else
            {
              juegox=Character.getNumericValue(entrada.charAt(0));
              juegoy=Character.getNumericValue(entrada.charAt(2));
              if(tabjugador[juegox][juegoy]=='.')
              {
               
                System.out.print("\033[H\033[2J");
                System.out.flush();
                if (tablero[juegox][juegoy] == 'b' || tablero[juegox][juegoy] == 'c' || tablero[juegox][juegoy] == 'd' || tablero[juegox][juegoy] == 'e')
                {
                  tabjugador[juegox][juegoy]='X';
                  mostrartablero();
                  System.out.println("Tocado.");
                  System.out.println(contbb + " " + contbc + " " + contbd + " " + contbd + " " + contbe);
                  switch(tablero[juegox][juegoy])
                  {
                      
                    case 'b':
                        contbb--;
                        if(contbb==0)
                        {
                            System.out.println("Hundido barco de dos posiciones.");
                        }
                        break;
                    case 'c':
                        contbc--;
                        if(contbc==0)
                        {
                            System.out.println("Hundido barco de tres posiciones.");
                        }
                        break;
                    case 'd':
                        contbd--;
                        if(contbd==0)
                        {
                            System.out.println("Hundido barco de cuatro posiciones.");
                        }
                        break;
                    case 'e':
                        contbe--;
                        if(contbe==0)
                        {
                            System.out.println("Hundido barco de cinco posiciones.");
                        }
                        break;
                  }
                }
                else
                {
                  tabjugador[juegox][juegoy]='a';
                  mostrartablero();
                  System.out.println("Agua.");
                }

                if(todosbarcoshundidos())
                {
                  sal=true;
                  System.out.println(" Felicidades, has hundido todos los barcos.");
                  sal=true;
                }
                else
                {
                  contjugadas++;
                  System.out.println("Jugadas - " + contjugadas + " de " + maxjugadas + ".");
                }
              }
              else
              {
                System.out.println("Ya habías jugado esta posición.");
              }
            }
          }
          else
          {
            System.out.println("Has agotado todas las jugadas.");
            sal=true;
          }
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        tabjugador=tablero;
        mostrartablero();
      }
      catch(Exception e){
          System.out.println("Error");
      }
    }

    private static boolean entradacorrecta(String entrada)
    {
      boolean correcto=false;
      int x, y;
      if(entrada.length()==3)
      {
        if(entrada.charAt(1)==',')
        {
         if(Character.isDigit(entrada.charAt(0)) && Character.isDigit(entrada.charAt(2)))
         {
             correcto=true;
         }    
        }
      }
      else if(entrada.length()==1)
      {
        if(entrada.charAt(0)=='X')
        {
          correcto = true;
        }
      }
      return correcto;
    }

    private static boolean todosbarcoshundidos()
    {
      boolean salx=false, encontrado=false, saly=false;
      int contx,conty;
      contx=0;
      while(!salx)
      {
        if(contx<tablero.length)
        {
          saly=false;
          conty=0;
          while(!saly)
          {
            if(conty<tablero[0].length)
            {
              if((tablero[contx][conty]=='b' || tablero[contx][conty] =='c' || tablero[contx][conty] == 'd' || tablero[contx][conty]=='e') && tabjugador[contx][conty]!='X')
              {
                salx=true;
                saly=true;
                encontrado=true;
              }
              else
              {
                conty++;
              }
            }
            else
            {
              saly=true;
            }
          }
          contx++;
        }
        else
        {
          salx=true;
        }
      }
      return !encontrado;
    }

    private static void inicializa()
    {
      tablero = new char[GRANDEX][GRANDEY];
      tabjugador = new char[GRANDEX][GRANDEY];
      tabmio = new char[GRANDEX][GRANDEY];
      tabjugadorma = new char[GRANDEX][GRANDEY];
      int conty,largo,decide;
      boolean vertical;
      for(int contx=0;contx<tablero.length;contx++)
      {
        for(conty=0;conty<tablero[0].length;conty++)
        {
          tablero[contx][conty]='a';
          tabjugador[contx][conty]='.';
          tabmio[contx][conty]='a';
          tabjugadorma[contx][conty]='.';
        }
      }

      for (largo=2;largo<=5;largo++)
      {
        decide = ((int)(Math.random()+0.5));
        if (decide==0)
        {
          vertical=true;
        }
        else
        {
          vertical=false;
        }
        colocarbarco(largo,vertical);
      }
      colocatusbarcos();
    }

    private static void colocatusbarcos()
    {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        int largo;
        String orientacion;
        String entrada;
        int posx,posy;
        boolean vertical;
        System.out.println("Vamos a colocar tus barcos en el tablero.");
        try
        {
            for (largo=2;largo<=5;largo++)
            {
                System.out.println("Vamos a introducir el barco de " + largo + " posiciones.");
                do
                {
                    System.out.println("Vertical u horizonal? v/h");
                    orientacion=lector.readLine();     
                }while(orientacion.charAt(0)!='v' && orientacion.charAt(0)!='h');
                if(orientacion.charAt(0)=='v')
                {
                    vertical=true;
                }
                else
                {
                    vertical=false;
                }
                
                do
                {
                    do {
                        System.out.println("Introduce coordernadas fila,columna");
                        entrada = lector.readLine();
                    } while (!entradacorrecta(entrada));
                    posx=Character.getNumericValue(entrada.charAt(0));
                    posy=Character.getNumericValue(entrada.charAt(2));
                 }while (!espaciolibre(posx,posy,largo,vertical,true));
                pintabarco(posx,posy,largo,vertical,true);
            }
        }
        catch(Exception e)
        {
            System.out.println("Error. Vamos a empezar de nuevo.");
            inicializa();
        }
        
    }
    
    private static void colocarbarco(int largo, boolean vertical)
    {
        int maximox,maximoy, posx,posy,decide;
        do {
          if(vertical)
          {
            maximoy = tablero[0].length -1 - largo -1;
            maximox = tablero.length -1;
          }
          else
          {
            maximox = tablero.length - 1 - largo - 1;
            maximoy = tablero[0].length -1;
          }
          posx = ((int)(Math.random() * (maximox + 1)));
          posy = ((int)(Math.random() * (maximoy + 1)));
      } while (!espaciolibre(posx,posy,largo,vertical,false));
      pintabarco(posx,posy,largo,vertical,false);
    }

    private static void mostrartablerori()
    {
      int contx,conty;
      System.out.println(" 0123456789");
      for(conty=0;conty<tablero[0].length;conty++)
      {
          System.out.print(conty);
          for(contx=0;contx<tablero.length;contx++)
          {
            if(contx==tablero.length-1)
            {
              System.out.println(tablero[contx][conty]);
            }
            else
            {
              System.out.print(tablero[contx][conty]);
            }
          }
      }
    }
    private static void mostrartablero()
    {
      int contx,conty;
      System.out.println("Mis barcos");
      System.out.println(" 0123456789");
      for(conty=0;conty<tabmio[0].length;conty++)
      {
          System.out.print(conty);
          for(contx=0;contx<tabmio.length;contx++)
          {
            if(contx==tabmio.length-1)
            {
              System.out.println(tabmio[contx][conty]);
            }
            else
            {
              System.out.print(tabmio[contx][conty]);
            }
          }
      }
      
      System.out.println("Máquina.");
      System.out.println(" 0123456789");
      for(conty=0;conty<tabjugador[0].length;conty++)
      {
          System.out.print(conty);
          for(contx=0;contx<tabjugador.length;contx++)
          {
            if(contx==tabjugador.length-1)
            {
              System.out.println(tabjugador[contx][conty]);
            }
            else
            {
              System.out.print(tabjugador[contx][conty]);
            }
          }
      }

    }

    private static void pintabarco(int posx, int posy, int largo, boolean vertical, boolean jugador)
    {
      int cont,inicio;
      char letra=' ';
      boolean sal = false;
      char posiciones[][];
      if(!jugador)
      {
          posiciones = tablero;
      }
      else
      {
          posiciones = tabmio;
      }
      if(vertical)
      {
        inicio=posy;
      }
      else
      {
        inicio=posx;
      }
      switch(largo)
      {
          case 2: 
              letra='b';
              break;
          case 3:
              letra='c';
              break;
          case 4:
              letra='d';
              break;
          case 5:
               letra='e';
               break;
          default:    
      }
      for(cont=inicio;cont<=inicio+largo-1;cont++)
      {
        if(vertical)
        {
          posiciones[posx][cont]=letra;
        }
        else
        {
          posiciones[cont][posy]=letra;
        }
      }
      if(!jugador)
      {
          tablero = posiciones;
      }
      else
      {
          tabmio = posiciones;
      }
    }

    private static boolean espaciolibre(int posx,int posy, int largo, boolean vertical,boolean jugador)
    {
      boolean sal = false, encontrado = false;
      int cont,contb, veces;
      char posiciones[][];
      if(!jugador)
      {
          posiciones= tablero;
      }
      else
      {
          posiciones = tabmio;
      }
      
      if(vertical)
      {
        contb = posx-1;
      }
      else
      {
        contb = posy-1;
      }
      for(veces=0;veces<3;veces++)
      {
        if(vertical)
        {
          cont=posy-1;
          if(veces==0)
          {
            contb=posx-1;
          }
          else if(veces==1)
          {
            contb=posx;
          }
          else
          {
            contb=posx+1;
          }
        }
        else
        {
          cont=posx-1;
          if(veces==0)
          {
            contb=posy-1;
          }
          else if(veces==1)
          {
            contb=posy;
          }
          else
          {
            contb=posy+1;
          }
        }
        sal=false;
        while(!sal)
        {
          if(vertical)
          {
            if(cont<=posy+largo)
            {
              if(cont<posiciones[0].length && cont>=0 && contb<posiciones.length && contb>=0)
              {
                if(posiciones[contb][cont]=='b' || posiciones[contb][cont]=='c' || posiciones[contb][cont]=='d' || posiciones[contb][cont]=='e')
                {
                  encontrado = true;
                  sal = true;
                }
                else
                {
                  cont++;
                }
              }
              else
              {
                cont++;
              }
            }
            else
            {
              sal=true;
            }
          }
          else
          {
            if(cont<=posx+largo)
            {
              if(cont<posiciones.length && cont>=0 && contb<posiciones[0].length && contb>=0)
              {
                if(posiciones[contb][cont]=='b' || posiciones[contb][cont]=='c' || posiciones[contb][cont]=='d' || posiciones[contb][cont]=='e')
                {
                  encontrado = true;
                  sal = true;
                }
                else
                {
                  cont++;
                }
              }
              else
              {
                cont++;
              }
            }
            else
            {
              sal=true;
            }
          }
        }
      }
      if(jugador && encontrado)
      {
          System.out.println("El barco no tiene espacio libre, introduce otra posición.");
      }
      return !encontrado;
    }   
}
