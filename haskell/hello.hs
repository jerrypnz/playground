data Gender = Male | Female deriving (Show)

data Person = Person { firstName :: String,
                       lastName :: String,
                       age :: Int,
                       gender :: Gender } deriving (Show)
