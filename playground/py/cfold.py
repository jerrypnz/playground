def cfold1(fun,z,list):
    if(len(list)==0):
       return z
    else:
       return fun(list.pop(),z,lambda y:cfold1 (fun,y,list))

def cfold(Operator,z,l):
   return  cfold1(lambda x,t,g:Operator(x,g(t)),z,l)

if __name__=="__main__":
    print aaa
    print cfold(lambda x,y:x+y,2,[1,2,3,4])
