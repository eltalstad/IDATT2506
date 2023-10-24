import React, { useState } from 'react';
import { TouchableOpacity, Text, TextInput, StyleSheet } from 'react-native';

const Cell = ({ value, onCellPress, hasThickRightBorder, isEditable, onValueChange }) => {
    const [isHighlighted, setIsHighlighted] = useState(false);

    const handleLongPress = () => {
        setIsHighlighted(!isHighlighted);
    };

    const commonStyles = [
        styles.cell,
        hasThickRightBorder && styles.thickRightBorder,
        isHighlighted && styles.highlighted
    ];

    if (isEditable) {
        return (
            <TextInput
                style={commonStyles}
                keyboardType="number-pad"
                maxLength={1}
                onChangeText={onValueChange}
                value={value ? value.toString() : ''}
                textAlign={'center'}
                textAlignVertical={'center'}
                onLongPress={handleLongPress}
            />
        );
    }

    return (
        <TouchableOpacity
            style={commonStyles}
            onPress={onCellPress}
            onLongPress={handleLongPress}
        >
            <Text style={styles.cellText}>{(typeof value === 'number' && value !== 0) ? value.toString() : ''}</Text>
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
    cell: {
        width: 40,
        height: 40,
        borderWidth: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },
    thickRightBorder: {
        borderRightWidth: 2
    },
    cellText: {
        fontSize: 20
    },
    highlighted: {
        backgroundColor: 'yellow'
    }
});

export default Cell;
