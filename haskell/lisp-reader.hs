import qualified Data.Map as Map

data Symbol = Symbol String deriving (Show, Eq, Ord)
data Val = VNil | VBool Bool | VString String | VInt Int | VSymbol Symbol | VLambda Lambda deriving (Show, Eq, Ord)
data Form = SExp [Form] | Atom Val deriving (Show, Eq, Ord)
data Env = Env { bindings :: Map.Map Symbol Val, parent :: Maybe Env} deriving (Show, Eq, Ord)
data Lambda = Lambda { body :: Form, env :: Env } deriving (Show, Eq, Ord)

emptyEnv:: Env
emptyEnv = Env {bindings

-- Lookup a binding in a Env
lookupEnv :: Env -> Symbol -> Val
lookupEnv env sym = case Map.lookup sym (bindings env) of
                     Just val -> val
                     Nothing -> case (parent env) of
                       Just p -> lookupEnv p sym
                       Nothing -> VNil


eval :: Form -> Env -> (Env, Val)
eval (Atom (VSymbol sym)) env = (env, lookupEnv env sym)
eval (Atom val) env = (env, val)
