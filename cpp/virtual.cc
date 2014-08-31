#include <iostream>

using namespace std;

class Animal {
     public:
           virtual void eat() = 0;
};
 
class Mammal : public virtual Animal {
     public:
           virtual void breathe() = 0;

           void eat() {
                cout << "Mammal eat" << endl;
           }
};
 
class WingedAnimal : public virtual Animal {
     public:
           virtual void flap() = 0;

           void eat() {
                cout << "WingedAnimal eat" << endl;
           }
};
 
class Bat : public Mammal, public WingedAnimal {
    public:
        void breathe() {
            cout << "Bat breathe" << endl;
        }

        void flap() {
            cout << "Bat flap" << endl;
        }

};
 
int main(int argc, const char *argv[]) {
    Bat bat;
    //bat.eat();

    WingedAnimal *winged = &bat;
    winged->eat();

    Mammal *mamm = &bat;
    mamm->eat();
    return 0;
}

