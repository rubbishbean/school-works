#####################################################################
#
# CAS CS 320, Fall 2014
# Assignment 4 (skeleton code)
# interpret.py
# modified by Jiaxing Yan 11/9/2014

exec(open("parse.py").read())

def subst(s, a):
    for label in a:
        children = a[label]
        if label == 'Variable':
            v = children[0]
            if v in s:
                sChildren = s[v]
                return sChildren
        else:
            for i in range(len(children)):
                sChildren = subst(s,children[i])
                if sChildren != None:
                    children[i] = sChildren
            return a
            
def unify(a, b):
    if type(a) != dict and type(b) != dict and a == b:
        return {}
    s = {}
    if "Variable" in b:
        children=b['Variable']
        [x] = children
        return {x:a}
    for label in a:
        childrenA = a[label]
        if label == 'Variable':
            x = a['Variable'][0]
            return{x:b}
        elif label in b:
            childrenB = b[label]
            if len(childrenA) == len(childrenB):
                for i in range(len(childrenA)):
                    cs = unify(childrenA[i],childrenB[i])
                    if cs != None:
                        s.update(cs)
                return s
            

def build(m, d):
    if d == 'End':
        return m
    else:
        for label in d:
            children = d[label]
            if label == 'Function':
                f = children[0]['Variable'][0]
                p = children[1]
                e = children[2]
                d2 = children[3]
                if f not in m:
                    m[f] = [(p,e)]
                else:
                    m[f].append((p,e))
                return build(m,d2)
            
                
  
def evaluate(m, env, e):
    if type(e) != dict:
        return e
    else:
        for label in e:
            children = e[label]
            if label == 'Apply':
                f = children[0]['Variable'][0]
                e1 = children[1]
                v1 = evaluate(m,env,e1)

                for t in m[f]:
                    (p,e2) = t
                    s = unify(p,v1)
                    if s!= None:
                        v2 = evaluate(m,s,e2)
                        return v2
                            
            elif label == 'Number':
                return e
            elif label == 'Variable':
                x = children[0]
                if x in env:
                    return env[x]
            elif label == 'Plus':
                e1 = children[0]
                e2 = children[1]
                n1 = evaluate(m,env,e1)
                n2 = evaluate(m,env,e2)
                return n1+n2
            elif label == 'ConBase':
                return e
            elif label == 'ConInd':
                c = children[0]
                e1 = children[1]
                e2 = children[2]
                v1 = evaluate(m,env,e1)
                v2 = evaluate(m,env,e2)
                return(c,v1,v2)
                    
                    

def interact(s):
    # Build the module definition.
    m = build({}, parser(grammar, 'declaration')(s))

    # Interactive loop.
    while True:
        # Prompt the user for a query.
        s = input('> ') 
        if s == ':quit':
            break
        
        # Parse and evaluate the query.
        e = parser(grammar, 'expression')(s)
        if not e is None:
            print(evaluate(m, {}, e))
        else:
            print("Unknown input.")

#eof
